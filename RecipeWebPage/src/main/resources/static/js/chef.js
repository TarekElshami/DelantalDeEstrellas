
const deleteBtn = document.getElementById('destroyChefBtn');
deleteBtn.addEventListener('click', () => {
    let id = deleteBtn.getAttribute('data-id');
    var confirmation = confirm("Are you sure you want to delete this item?");
    if (confirmation) {
        window.location.href = "/chefs/"+id+"/deleted"
    } else {
        // User clicked Cancel
        alert("Deletion cancelled."); // Optional feedback

    }
})