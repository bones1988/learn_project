const refreshToken = async () => {
    let refresh = localStorage.getItem('Refresh');
    if (!refresh) {
        localStorage.clear();
    } else {
        let refreshBody = {
            refresh: refresh
        }
        let response = (
            fetch('http://localhost:8080/users/refresh', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(refreshBody),
            })
        );
        let res = await response
        if (res.status === 200) {
            let json = await res.json();
            let bearer = json.bearer;
            let refresh = json.refresh;
            localStorage.setItem('Bearer', bearer);
            localStorage.setItem('Refresh', refresh)
        } else {
            localStorage.clear();
        }
    }
}


export default refreshToken;
