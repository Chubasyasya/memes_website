const fileInputsDiv = document.getElementById("fileInputs");

function addFileInput() {
    const inputCount = fileInputsDiv.querySelectorAll("input[type='file']").length + 1;

    const newInput = document.createElement("input");
    newInput.setAttribute("type", "file");
    newInput.setAttribute("name", "publication-image");
    newInput.setAttribute("id", `publication-image-${inputCount}`);
    newInput.setAttribute("accept", ".jpg, .jpeg, .png");

    newInput.addEventListener("change", addFileInput);

    fileInputsDiv.appendChild(newInput);
}

document.getElementById("publication-image-1").addEventListener("change", addFileInput);
