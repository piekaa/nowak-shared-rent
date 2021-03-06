package com.piekoszek.nowaksharedrent.invite;

import com.piekoszek.nowaksharedrent.apartment.Apartment;
import com.piekoszek.nowaksharedrent.apartment.ApartmentService;
import com.piekoszek.nowaksharedrent.user.UserService;
import com.piekoszek.nowaksharedrent.invite.exceptions.InviteCreatorException;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
class InviteServiceImpl implements InviteService {

    private InvitationRepository invitationRepository;
    private ApartmentService apartmentService;
    private UserService userService;
    private UuidService uuidService;

    @Override
    public void createInvitation(String from, String to, String apartmentId) {
        if (!userService.isAccountExists(to)) {
            throw new InviteCreatorException("User with email " + to + " not found!");
        }
        if (invitationRepository.existsByReceiverAndApartmentId(to, apartmentId)) {
            throw new InviteCreatorException("Duplicated invitation found!");
        }
        Apartment apartment = apartmentService.getApartment(apartmentId);
        if (!apartment.getAdmin().equals(from)) {
            throw new InviteCreatorException("You're not an administrator of this apartment!");
        }
        if (apartmentService.hasTenant(apartment, to)) {
            throw new InviteCreatorException("User is already tenant of this apartment!");
        }

        invitationRepository.save(Invitation.builder()
                .id(uuidService.generateUuid())
                .sender(from)
                .receiver(to)
                .apartmentId(apartmentId)
                .apartmentName(apartment.getAddress() + ", " + apartment.getCity())
                .build());
    }

    @Override
    public Optional<String> resolveInvitation(String to, String id, boolean isAccepted) {
        Invitation existingInvitation = invitationRepository.findById(id);
        if (existingInvitation != null && existingInvitation.receiver.equals(to)) {
            invitationRepository.deleteById(id);
            if (isAccepted) {
                apartmentService.addTenant(to, existingInvitation.apartmentId);
                return Optional.of("Invitation accepted");
            }
            return Optional.of("Invitation rejected");
        }
        return Optional.empty();
    }

    @Override
    public List<Invitation> getInvitations(String to) {
        return invitationRepository.findAllByReceiver(to);
    }
}
