function confirmDelete(id) {
    if (confirm("¿Estás seguro de que quieres borrar este ingrediente?")){
        fetch("/IsIngredientBeingUsed?id="+id)
            .then( response => {
                return response.json();
            }).then( data => {
                if (data) {
                    alert("No se puede borrar este ingrediente ya que se está usando en al menos una receta.")
                    return false;
                }
            })

    }
    return false;
}

