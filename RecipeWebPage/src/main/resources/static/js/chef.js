
const deleteBtn = document.getElementById('destroyChefBtn');
const changeInfoBtn = document.getElementById('changeInfo');
const addRecipe= document.getElementById('addRecipte');
deleteBtn.addEventListener('click', () => {
    let id = deleteBtn.getAttribute('data-id');
    var confirmation = confirm("¿Seguro que quieres borrar este Chef?");
    if (confirmation) {
        window.location.href = "/chef/"+id+"/delete"
    } else {
        // User clicked Cancel
        alert("Deletion cancelled."); // Optional feedback

    }
})

changeInfoBtn.addEventListener('click', ()=> {
    window.location.href = '/chef/'+ changeInfoBtn.getAttribute('data-id')+'/update';
})

addRecipe.addEventListener('click', () => {
    window.location.href= '/recipe/new?id=' + addRecipe.getAttribute('data-id');
})