function loginRequest(data) {
  return (
    fetch('http://localhost:8080/users/authentication', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(data),
    })
  );
}

export default loginRequest;
