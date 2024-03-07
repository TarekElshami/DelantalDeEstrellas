let chefItems = document.getElementsByClassName("chef-item");
const popup = document.getElementById("portfolioModal");
const popupTitle = document.getElementById('chef-name');
const popupDescription = document.getElementById('chef-description');
const popupImage = document.getElementById('chef-image');
const popupBtn = document.getElementById('popup-btn');

for (const chefItem of chefItems) {
    chefItem.addEventListener('click', (e) => {
            let element = e.target.closest('.chef-item')
            let img = element.querySelector('img').getAttribute('src')
            console.log(img)
            let name = element.getAttribute('data-name')
            let description = element.getAttribute('data-description')
            popupBtn.setAttribute('data-id', element.getAttribute('data-id'))
            popupTitle.textContent = name;
            popupImage.src = img;
            popupDescription.textContent = description;
            popup.style.display = "block"
        }
    )
}

const closePopUpBtn = document.getElementById("close-popup");
closePopUpBtn.addEventListener("click", () => {
    popup.style.display = "none"
})

popupBtn.addEventListener('click', () => {
    let id = popupBtn.getAttribute('data-id')
    window.location.href = '/chef/' + id;
})



