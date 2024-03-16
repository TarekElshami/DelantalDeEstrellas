
const deleteBtn = document.getElementById('destroyChefBtn');
const changeInfoBtn = document.getElementById('changeInfo');
deleteBtn.addEventListener('click', () => {
    let id = deleteBtn.getAttribute('data-id');
    var confirmation = confirm("Are you sure you want to delete this item?");
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