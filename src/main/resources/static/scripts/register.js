function register() {

    let email = document.getElementById("emailInput").value;
    let name = document.getElementById("nameInput").value;
    let password = document.getElementById("passInput").value;
    let repassword = document.getElementById("repassInput").value;
    let url = '/auth/signup';
    let data = { "email": email, "name": name, "password": password };

    if (password !== repassword) {
        document.getElementById("error").innerHTML = "Not the same passwords";
        document.getElementById("error").style.color = "#ff0000";
        return false;
    }
    else if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email))) {
        document.getElementById("error").innerHTML = "Wrong email address";
        document.getElementById("error").style.color = "#ff0000";
        return false;
    }
    else if (!(password.length > 3)) {
        document.getElementById("error").innerHTML = "Too short password";
        document.getElementById("error").style.color = "#ff0000";
        return false;
    }
    else {
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            let errorElement = document.getElementById("error");
            if (res.ok) {
                res.json().then(json => {
                    window.localStorage.setItem("accessToken", json.token);
                    console.log('Success:', JSON.stringify(json));
                    window.location.href = 'index.html';
                })
            }
            else {
                res.json().then(json => {
                    console.log('Internal error:', JSON.stringify(json));
                    errorElement.innerHTML = "Account with such email already exists!";
                    errorElement.style.color = "#ff0000";
                })
            }
        })
            .catch(error => console.error('Error:', error));

        return false;
    }
}