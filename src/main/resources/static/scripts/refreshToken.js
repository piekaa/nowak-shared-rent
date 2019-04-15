function refreshToken() {
    
    let url = '/auth/refresh';
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Authorization', window.localStorage.getItem("accessToken"));

    fetch(url, {
        method: 'GET',
        headers: headers
    })
    .then(res => {
        if(res.ok) {
          res.json().then(json => {
            window.localStorage.setItem("accessToken", json.token);
            console.log('Success:', JSON.stringify(json));
          })
        }
        else {
          res.json().then(json => {
            console.log('Internal error:', JSON.stringify(json));
          })
        }
      })
      .catch(error => console.error('Error:', error));
}