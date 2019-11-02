window.addEventListener('load', function () {
    var newDetailTitle = document.getElementById("new_detail_title");
    var newDetailDescription = document.getElementById("new_detail_value");
    var newAddButton = document.getElementById("new_detail_button");
    var detailsBody = document.getElementById("details_body");

    newAddButton.addEventListener("click", function (e) {
        e.preventDefault();

        var title = newDetailTitle.value.trim();
        var value = newDetailDescription.value.trim();

        if (title.length === 0 && value.length === 0)
            return;

        addRow(title, value);
        clear();
    });

    function clear() {
        newDetailTitle.value = "";
        newDetailDescription.value = "";
    }


    function addRow(title, value) {
        var index = detailsBody.childElementCount - 1;
        var row = document.createElement("tr");

        var titleCell = document.createElement("td");
        var titleInput = document.createElement("input");

        titleInput.classList.add("form-control");
        titleInput.value = title;
        titleInput.name = "details[" + index + "].title";
        titleInput.dataset.target = 'title';
        titleInput.dataset.details = 'true';

        titleCell.appendChild(titleInput);

        var valueCell = document.createElement("td");
        var valueInput = document.createElement("input");

        valueInput.classList.add("form-control");
        valueInput.value = value;
        valueInput.name = "details[" + index + "].value";
        valueInput.dataset.target = 'value';
        valueInput.dataset.details = 'true';

        valueCell.appendChild(valueInput);

        var removeCell = document.createElement("td");
        var removeButton = document.createElement("a");
        removeButton.href = "#";
        removeButton.classList.add("btn", "btn-danger");
        removeButton.innerText = "Удалить";
        removeButton.setAttribute("onclick", "window.removeDetailNode(this); return false;");

        removeButton.addEventListener("click", function (e) {
            e.preventDefault();
            row.remove();
        });

        removeCell.appendChild(removeButton);

        row.appendChild(titleCell);
        row.appendChild(valueCell);
        row.appendChild(removeCell);

        detailsBody.prepend(row);

        recalculateIndexes();
    }

    function recalculateIndexes() {
        for (var i = 0; i < detailsBody.childElementCount; i++) {
            var tr = detailsBody.children[i];
            var inputs = tr.querySelectorAll("input[data-target]");

            for (var j = 0; j < inputs.length; j++) {
                var input = inputs[j];
                input.name = "details[" + i + "]." + input.dataset.target;
            }
        }
    }

    function removeDetailNode(node) {
        node.parentNode.parentNode.remove();
        recalculateIndexes();
    }

    window.removeDetailNode = removeDetailNode;
});
