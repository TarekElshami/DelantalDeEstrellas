
const deleteBtn = document.getElementById('destroyChefBtn');
const changeInfoBtn = document.getElementById('changeInfo');
const addRecipe= document.getElementById('addRecipe');
deleteBtn.addEventListener('click', () => {
    let id = deleteBtn.getAttribute('data-id');
    var confirmation = confirm("¿Seguro que quieres borrar este Chef?");
    if (confirmation) {
        if (document.getElementById("name").textContent === "Anónimo"){
            alert("No es posible borrar este Chef.")
            return;
        }
        window.location.href = "/chef/"+id+"/delete"
    } else {
        // User clicked Cancel
        alert("Deletion cancelled."); // Optional feedback

    }
})

changeInfoBtn.addEventListener('click', ()=> {
    if (document.getElementById("name").textContent === "Anónimo"){
        alert("No es posible editar este Chef.")
        return;
    }
    window.location.href = '/chef/'+ changeInfoBtn.getAttribute('data-id')+'/update';
})


addRecipe.addEventListener('click', () => {
    window.location.href= '/recipe/new?id=' + addRecipe.getAttribute('data-id');
})