
function filterIngredients(){
    document.getElementById("matchingIngredientsList").setAttribute("data-selected", "");
    disableAddIngredientButton();
    let input = document.getElementById("ingredientInput").value.toLowerCase();
    fetch("/api/ingredients")
        .then( response => {
            return response.json();
        }).then( data => {
            let list = document.getElementById("matchingIngredientsList");
            list.innerHTML = '';
            for (let key in data){
                let ingredient = data[key]
                let ingredientName = ingredient.name.toLowerCase()
                if (ingredientName.includes(input)){
                    let htmlToAdd = `<li id="tempIng${ingredient.id}" onclick="select(${ingredient.id})">${ingredientName}</li>`;
                    list.insertAdjacentHTML('beforeend', htmlToAdd);
                }
            }
        })
}

function select(id){
    let list = document.getElementById("matchingIngredientsList");
    for (let child of list.children){
        child.classList.remove("selected");
    }
    document.getElementById("tempIng"+id).classList.add("selected");
    list.setAttribute("data-selected", id);
    document.getElementById("addIngButton").disabled = false;
}

function addIngredient(){
    let list = document.getElementById("matchingIngredientsList");
    let id = list.getAttribute("data-selected");
    let selected = document.getElementById("tempIng"+id);
    emptyIngredientModal();
    if (id==="") return;
    if (document.getElementById("ing"+id) !== null){
        alert("Este ingrediente ya está en la receta");
        return;
    }
    let htmlContent = `<li id="ing${id}" data-id="${id}" onclick="removeIngredient(${id})">${selected.innerText}</li>`
    document.getElementById("ingList").insertAdjacentHTML("beforeend", htmlContent);
}

function emptyIngredientModal(){
    document.getElementById("ingredientInput").value = "";
    let list = document.getElementById("matchingIngredientsList");
    list.innerHTML = "";
    list.setAttribute("data-selected", "");
}

function removeIngredient(id){
    document.getElementById("ing"+id).remove();
}

function disableAddIngredientButton(){
    document.getElementById("addIngButton").disabled = true;
}

function addStep(){
    let input = document.getElementById("stepInput").value;
    if (input==="") {
        alert("No se puede añadir un paso vacío");
        return;
    }
    let stepsList = document.getElementById("stepsList");
    let steps = stepsList.children.length+1;
    let htmlContent = `<li id="step${steps}" class="lead" data-bs-toggle="modal" data-bs-target="#editStepModal" onclick="editStep(${steps})">${input}</li>`
    stepsList.insertAdjacentHTML("beforeend", htmlContent);
    document.getElementById("stepInput").value = "";
}

function updateChefContent(id){
    let chef = document.getElementById("chef"+id)
    let chefLink = document.getElementById("chefLink");
    chefLink.setAttribute("href", "/chef/"+id);
    let chefName = document.getElementById("chefName");
    chefName.innerText = chef.innerText;
    let chefDescription = document.getElementById("chefDescription");
    chefDescription.innerText = chef.getAttribute("data-description");
}

function editStep(num){
    document.getElementById("editStepInput").value = document.getElementById("step"+num).innerText;
    document.getElementById("editStepButton").onclick = function () {saveEditedStep(num)};
    document.getElementById("deleteStepButton").onclick = function (){deleteStep(num)};
}

function saveEditedStep(num){
    let step = document.getElementById("step"+num);
    if (step.innerText === ""){
        alert("No se puede dejar un paso vacío");
        return;
    }
   step.innerText = document.getElementById("editStepInput").value;
}

function deleteStep(num){
    document.getElementById("step"+num).remove();
    updateStepIds()
}

function updateStepIds() {
    let i= 0;
    for (let child of document.getElementById("stepsList").children){
        let j = i;
        child.setAttribute("id", "step"+j.toString());
        child.onclick = function() {editStep(j)}
        i++;
    }
}

function validateAndSendForm(URL){
    if (!validateForm(URL)) return;
    let body = assembleBody();
    sendForm(body, URL)
}

function sendForm(body, URL) {
    fetch(URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
        .then(response => {
            //if there was an error, say there was an error, else, say recipe created and redirect
            if (response.status === 200) {
                if (URL.includes("update")) {
                    alert("Receta editada correctamente");
                    window.location.href = URL.split("/").slice(0,-1).join("/");
                } else {
                    alert("Receta creada correctamente");
                    window.location.href = "/recipes";
                }
            } else {
                alert("Lo sentimos, ha habido un error. No ha habido cambios en al información almacenada")
            }
        })
}

function validateForm(URL) {
    let ogURL = window.location.pathname
    if (URL !== ogURL) {
        alert("Que haces tocando donde nadie te manda tocar");
        return false;
    }

    let img = document.getElementById("recipeImg").value
    let name = document.getElementById("nameInput").value;
    let description = document.getElementById("descriptionInput").value
    if (name === "" || description === "" || img === ""){
        alert("La receta debe tener un nombre, descripción, e imagen");
        return false;
    }
    let ingredientsList = document.getElementById("ingList");
    if (ingredientsList.children.length <= 0){
        alert("La receta debe tener al menos un ingrediente.")
        return false;
    }
    let stepsList = document.getElementById("stepsList");
    if (stepsList.children.length <= 0){
        alert("La receta debe tener al menos un paso.")
        return false;
    }
    let chef = document.getElementById("chefInput").value
    if (chef === "noChef"){
        alert("La receta debe tener un chef asignado.")
        return false;
    }
    return true;
}

function assembleBody() {
    let name = document.getElementById("nameInput").value;
    let description = document.getElementById("descriptionInput").value;
    let image = document.getElementById("recipeImg").value;
    let ingredientsList = document.getElementById("ingList");
    let stepsList = document.getElementById("stepsList");
    let chef = document.getElementById("chefInput").value;
    if (chef==="noChef") chef = "-1"

    let ingredients = [];
    for(let child of ingredientsList.children){
        ingredients.push(child.getAttribute("data-id"));
    }
    let steps = [];
    for(let child of stepsList.children){
        steps.push(child.innerText);
    }



    return {
        name: name,
        description: description,
        image: image,
        ingredients: ingredients,
        steps:steps,
        chef:chef
    }
}


document.getElementById('ingredientInput').addEventListener('keydown', event => {if (event.key === "Enter") event.preventDefault();})
document.getElementById('stepInput').addEventListener('keydown', event => {if (event.key === "Enter") event.preventDefault();})
document.getElementById("addIngModal").addEventListener("hidden.bs.modal", function () {emptyIngredientModal();})
document.addEventListener('DOMContentLoaded', function() {
    updateStepIds();
    let id = document.getElementById("chefInput").getAttribute("data-original-chef");
    for (let child of document.getElementById("chefInput").children){
        child.removeAttribute("selected");
    }
    document.getElementById("chef"+id).setAttribute("selected", "selected");
})
