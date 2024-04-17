let chefItems = document.getElementsByClassName("chef-item");
const popup = document.getElementById("popup-window");
const popupTitle = document.getElementById('chef-name');
const popupDescription = document.getElementById('chef-description');
const popupImage = document.getElementById('chef-image');
const popupBtn = document.getElementById('popup-btn');
const closeBtn = document.getElementById('clsBtn');
const showMore = document.getElementById('showMore');

const closePopUp = () => {
    popup.style.display = "none"
}

const chefEvent = (e) => {
    let element = e.target.closest('.chef-item')
    let img = element.querySelector('img').getAttribute('src')
    let name = element.getAttribute('data-name')
    let description = element.getAttribute('data-description')
    popupBtn.setAttribute('data-url', element.getAttribute('data-url'))
    popupTitle.textContent = name;
    popupImage.src = img;
    popupDescription.textContent = description;
    popup.style.display = "block"
}

for (const chefItem of chefItems) {
    chefItem.addEventListener('click', (e) => {
        chefEvent(e)
    })
}

const closePopUpBtn = document.getElementById("close-popup");
closePopUpBtn.addEventListener("click", () => {
    closePopUp();
})

closeBtn.addEventListener('click', () => {
    closePopUp();
})

popupBtn.addEventListener('click', () => {
    let url = popupBtn.getAttribute('data-url')
    closePopUp()
    window.location.href = url;
})

window.addEventListener('click', (e) => {e.target === popup && closePopUp()})

showMore.addEventListener('click', () => {
    let page = showMore.getAttribute('data-nextPage');
    fetch(`/loadMoreChefs?page=`+page)
        .then(response => response.text())
        .then(html => {
            document.getElementById('chef-container').insertAdjacentHTML('beforeend', html);
        });
    let nextPage = parseInt(page,10) + 1;
    showMore.setAttribute("data-nextPage",nextPage);
})

const repeatableObserver = (btn) => {
    return new MutationObserver( m => {
        m.forEach(m => {
            m.addedNodes.forEach(n => {

                if (n.nodeType === Node.ELEMENT_NODE && n.classList.contains('noMore')){
                    btn.style.display = "none";
                } else if (n.nodeType === Node.ELEMENT_NODE){
                    n.firstElementChild.addEventListener('click', (e) => {chefEvent(e)})
                }

            })
        })
    })
}

let createdEventsObserver = repeatableObserver(showMore);
createdEventsObserver.observe(document.getElementById('chef-container'), {childList: true});