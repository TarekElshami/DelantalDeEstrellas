
function loadMore(page){
    let button = document.getElementById("showMore");
    button.onclick = function () {loadMore(page+1)};
    fetch("/NextRecipePage?page="+page)
        .then(response => {
            if (response.status===204 || response.headers.get("X-Is-Last-Page")){
                button.style.display = "none"
            }
            return response.json()
        }).then(recipes => {
            for(let recipePos in recipes){
                let recipe = recipes[recipePos]
                let firstFragment = `<div class="col-md-6 col-lg-4 mb-5">
                        <div class="portfolio-item mx-auto" data-bs-toggle="modal" data-bs-target="#portfolioModal${recipe.id}">
                            <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                <div class="portfolio-item-caption-content text-center text-white"><i class="fas fa-plus fa-3x"></i></div>
                            </div>
                            <img class="img-fluid" src="/img/${recipe.img}" alt="..." />
                        </div>
                    </div>`
                let secondFragment = `<div class="portfolio-modal modal fade" id="portfolioModal${recipe.id}" tabindex="-1" aria-labelledby="portfolioModal${recipe.id}" aria-hidden="true">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header border-0"><button class="btn-close" type="button" data-bs-dismiss="modal" aria-label="Close"></button></div>
                    <div class="modal-body text-center pb-5">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-8">
                                    <!-- Portfolio Modal - Title-->
                                    <h2 class="portfolio-modal-title text-secondary text-uppercase mb-0">${recipe.name}</h2>
                                    <!-- Icon Divider-->
                                    <div class="divider-custom">
                                        <div class="divider-custom-line"></div>
                                        <div class="divider-custom-icon"><i class="fas fa-star"></i></div>
                                        <div class="divider-custom-line"></div>
                                    </div>
                                    <!-- Portfolio Modal - Image-->
                                    <img class="img-fluid rounded mb-5" src="/img/${recipe.img}" alt="..." />
                                    <!-- Portfolio Modal - Text-->
                                    <p class="mb-4">${recipe.description}</p>
                                    <a class="btn btn-primary"  href="/recipe/${recipe.id}">
                                        Más Información
                                    </a>
                                    <button class="btn btn-primary" data-bs-dismiss="modal">
                                        <i class="fas fa-xmark fa-fw"></i>
                                        Close Window
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`
                button.insertAdjacentHTML('beforebegin', firstFragment)
                document.getElementById("infoMarker").insertAdjacentHTML('beforebegin', secondFragment)
            }
        })
}