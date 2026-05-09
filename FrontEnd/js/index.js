var backend = "http://localhost:8080";
var globalstate = { user: null, token: null };
var app;

function loaded(){
    app = new App();
    document.querySelector('#root').replaceChildren(app.dom);
}

document.addEventListener("DOMContentLoaded", loaded);

function errorMessage(code){
    alert(`Error. Status: ${code}`);
}

async function fetchAPI(endpoint, method = 'GET', body = null)
{
    const headers = {
        'Content-Type': 'application/json'
    };

    if (globalstate.token) {
        headers['Authorization'] = `Bearer ${globalstate.token}`;
    }

    const options = { method, headers };
    if (body) {
        options.body = JSON.stringify(body);
    }

    const response = await fetch(`${backend}${endpoint}`, options);

    if (response.status === 401 || response.status === 403) {
        alert("No tienes permisos suficientes o tu sesión expiró.");
        app.logout();
        throw new Error("No autorizado");
    }

    return response;
}