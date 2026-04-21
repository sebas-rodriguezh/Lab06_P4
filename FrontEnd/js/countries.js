class Countries {
    dom;
    modal;
    state;

    constructor() {
        this.state = { 'entities': new Array(), 'entity': this.emptyEntity(), 'mode': 'A' };
        this.dom = this.render();
        this.modal = new bootstrap.Modal(this.dom.querySelector('#modal'));

        this.dom.querySelector("#create").addEventListener('click', () => this.makenew());
        this.dom.querySelector("#search").addEventListener('click', () => this.search());
        this.dom.querySelector("#modal #apply").addEventListener('click', () => this.save());
    }

    render = () => {
        const html = `
            ${this.renderList()}
            ${this.renderModal()}    
        `;
        var rootContent = document.createElement('div');
        rootContent.id = 'countries';
        rootContent.innerHTML = html;
        return rootContent;
    }

    renderList = () => {
        return `
        <div id="list" class="container">     
            <div class="card bg-light">
                <h4 class="card-title mt-3 text-center">Countries</h4>    
                <div class="card-body mx-auto w-75" >
                    <form id="form">
                        <div class="input-group mb-3">
                            <span class="input-group-text">Name</span>
                            <input id="name" type="text" class="form-control">
                          <div class="btn-toolbar">
                            <div class="btn-group me-2"><button type="button" class="btn btn-primary" id="search">Search</button> </div>
                            <div class="btn-group me-2"><button type="button" class="btn btn-primary" id="create">Create</button> </div>                        
                          </div>  
                        </div>
                    </form>

                    <div class="table-responsive " style="max-height: 300px; overflow: auto">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Name</th>
                                    <th scope="col">Flag</th>
                                    <th scope="col">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="listbody">
                            </tbody>
                        </table>
                    </div>                 
                </div>
            </div>
        </div>
        `;
    }

    renderModal = () => {
        return `
        <div id="modal" class="modal fade" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header" >
                        <img class="img-circle" id="img_logo" src="images/logo.png" style="max-width: 50px; max-height: 50px" alt="logo">
                        <span style='margin-left:4em;font-weight: bold;'>País</span> 
                       <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form id="form" >
                    <div class="modal-body">
                        
                        <div class="input-group mb-3">
                            <span class="input-group-text">Nombre</span>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Capital</span>
                            <input type="text" class="form-control" id="capital" name="capital" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Población</span>
                            <input type="number" class="form-control" id="population" name="population" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Área</span>
                            <input type="number" class="form-control" id="area" name="area" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Latitud</span>
                            <input type="number" class="form-control" id="lat" name="lat" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">Longitud</span>
                            <input type="number" class="form-control" id="lng" name="lng" required>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">URL de Bandera</span>
                            <input type="text" class="form-control" id="flag" name="flag" required>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button id="apply" type="button" class="btn btn-primary" >Aplicar</button>
                    </div>
                    </form>                 
                </div>         
            </div>          
        </div>      
        `;
    }

    showModal = async () => {
        // Cargar los datos de la entidad a los inputs del modal
        document.querySelector("#modal #name").value = this.state.entity.name;
        document.querySelector("#modal #capital").value = this.state.entity.capital;
        document.querySelector("#modal #population").value = this.state.entity.population;
        document.querySelector("#modal #area").value = this.state.entity.area;

        document.querySelector("#modal #lat").value = this.state.entity.latlng[0] !== undefined ? this.state.entity.latlng[0] : '';
        document.querySelector("#modal #lng").value = this.state.entity.latlng[1] !== undefined ? this.state.entity.latlng[1] : '';

        document.querySelector("#modal #flag").value = this.state.entity.flag;

        document.querySelector("#modal #name").disabled = (this.state.mode === 'E');

        this.modal.show();
    }

    load = () => {
        this.state.entity.name = document.querySelector("#modal #name").value;
        this.state.entity.capital = document.querySelector("#modal #capital").value;
        this.state.entity.population = parseInt(document.querySelector("#modal #population").value) || 0;
        this.state.entity.area = parseInt(document.querySelector("#modal #area").value) || 0;

        const lat = parseInt(document.querySelector("#modal #lat").value) || 0;
        const lng = parseInt(document.querySelector("#modal #lng").value) || 0;
        this.state.entity.latlng = [lat, lng];

        this.state.entity.flag = document.querySelector("#modal #flag").value;
    }

    reset = () => {
        this.state.entity = this.emptyEntity();
    }

    emptyEntity = () => {
        return {
            name: "",
            capital: "",
            population: 0,
            area: 0,
            latlng: [0, 0],
            flag: ""
        };
    }

    validate = () => {
        const name = document.querySelector("#modal #name").value;
        if (name.trim() === "") {
            alert("El nombre del país es requerido.");
            return false;
        }
        return true;
    }

    save = async () => {
        if (!this.validate()) return;
        this.load();

        const isEdit = this.state.mode === 'E';
        const url = isEdit ? `${backend}/countries/${this.state.entity.name}` : `${backend}/countries`;
        const method = isEdit ? 'PUT' : 'POST';

        const request = new Request(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(this.state.entity)
        });

        try {
            const response = await fetch(request);
            if (!response.ok) { errorMessage(response.status); return; }

            this.list();
            this.reset();
            this.modal.hide();
        } catch (error) {
            console.error("Error al guardar:", error);
        }
    }

    list = () => {
        const request = new Request(`${backend}/countries`, { method: 'GET', headers: {} });
        (async () => {
            try {
                const response = await fetch(request);
                if (!response.ok) { errorMessage(response.status); return; }

                var countries = await response.json();
                this.state.entities = countries;

                var listing = this.dom.querySelector("#countries #list #listbody");
                listing.innerHTML = "";
                this.state.entities.forEach(e => this.row(listing, e));
            } catch (error) {
                console.error("Error al listar:", error);
            }
        })();
    }

    row = (list, c) => {
        var tr = document.createElement("tr");
        tr.innerHTML = `
                <td>${c.name}</td>
                <td><img class="flag" src="${c.flag}" style="max-height: 30px;"></td>
                <td>
                    <i class="fas fa-pencil-alt text-primary edit-btn" style="cursor:pointer;" title="Editar"></i>
                </td>`;

        tr.querySelector(".edit-btn").addEventListener('click', () => this.prepareEdit(c.name));

        list.append(tr);
    }

    makenew = () => {
        this.reset();
        this.state.mode = 'A';
        this.showModal();
    }

    prepareEdit = (name) => {
        const found = this.state.entities.find(e => e.name === name);
        if (found) {
            this.state.entity = { ...found };
            this.state.mode = 'E';
            this.showModal();
        }
    }

    search = async () => {
        var nombre = document.querySelector('#countries #list #form #name').value;
        let request = new Request(`${backend}/countries?name=${nombre}`, { method: 'GET', headers: {} });

        try {
            let response = await fetch(request);
            if (!response.ok) { errorMessage(response.status); return; }

            this.state.entities = await response.json();
            var listing = this.dom.querySelector("#countries #list #listbody");
            listing.innerHTML = "";
            this.state.entities.forEach(e => this.row(listing, e));
        } catch (error) {
            console.error("Error al buscar:", error);
        }
    }
}