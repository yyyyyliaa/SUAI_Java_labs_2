const mainList = document.getElementById('mainList');

Object.entries(data).forEach(([category, items], index) => {
    const li = document.createElement('li');
    li.classList.add("list-group-item");

    const deleteButton = document.createElement('span');
    deleteButton.innerHTML = "[X]   ";
    deleteButton.classList.add("delete-button");
    deleteButton.onclick = () => {
        li.remove();
    };

    const span = document.createElement('span');
    const nestedUl = document.createElement('ul');
    nestedUl.classList.add("collapse");

    span.innerText = `${index + 1}. ${category} [+]`;
    span.onclick = () => {
        if (nestedUl.style.display === 'none') {
            nestedUl.style.display = 'block';
            span.innerText = `${index + 1}. ${category} [-]`;
        } else {
            nestedUl.style.display = 'none';
            span.innerText = `${index + 1}. ${category} [+]`;
        }
    };

    items.forEach(item => {
        const nestedLi = document.createElement('li');
        nestedLi.classList.add("list-group-item");
        nestedLi.innerText = item.trim();

        const nestedDeleteButton = document.createElement('span');
        nestedDeleteButton.innerHTML = "[X] "; // Вставляем крестик [X]
        nestedDeleteButton.classList.add("delete-button");
        nestedDeleteButton.onclick = () => {
            nestedLi.remove();
        };

        nestedLi.appendChild(nestedDeleteButton);
        nestedUl.appendChild(nestedLi);
    });

    li.appendChild(deleteButton);
    li.appendChild(span);
    li.appendChild(nestedUl);
    mainList.appendChild(li);
});
