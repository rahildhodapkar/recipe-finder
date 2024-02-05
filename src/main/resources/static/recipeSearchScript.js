$(document).ready(function() {
    function checkInputsAndDeleteButton() {
        var isAnyFieldFilled = false;
        var fieldCount = $('.input').length;
        $('.input').each(function() {
            if (this.value !== '') {
                isAnyFieldFilled = true;
            }
        });
        $('#submitBtn').prop('disabled', !isAnyFieldFilled);

        // Show or hide the delete button based on the number of input fields
        if (fieldCount > 1) {
            $('.deleteFieldBtn').show();
        } else {
            $('.deleteFieldBtn').hide();
        }
    }

    function addDeleteButtonFunctionality() {
        $('.deleteFieldBtn').click(function() {
            $(this).closest('.input-container').remove();
            checkInputsAndDeleteButton();
        });
    }

    $('#addFieldBtn').click(function() {
        var inputContainer = $('<div class="input-container"></div>');
        var newField = $('<input type="text" class="input" name="field[]" value="" placeholder="Add ingredient"/>');
        var deleteBtn = $('<button type="button" class="deleteFieldBtn">Delete</button>');

        inputContainer.append(newField).append(deleteBtn);
        inputContainer.insertBefore('.buttons-container');

        newField.change(checkInputsAndDeleteButton);
        addDeleteButtonFunctionality();
        checkInputsAndDeleteButton();
    });

    $('#refreshForm').click(function() {
        // Clear all input fields
        $('.input-container').not(':first').remove();
        $('.input-container:first .input').val('');
        // Hide the recipes container
        $('#resultsContainer').empty().hide();
        checkInputsAndDeleteButton();
    });

    addDeleteButtonFunctionality();
    $('.input').change(checkInputsAndDeleteButton);
    // Initially hide the recipes container
    $('#resultsContainer').hide();
    checkInputsAndDeleteButton();

    $('#recipeSearchForm').submit(function(event) {
        event.preventDefault();
        var formData = $(this).serialize();

        $.ajax({
            type: "POST",
            url: "/recipeSearch",
            data: formData,
            success: function(recipes) {
                var htmlContent = '';
                recipes.forEach(function(recipe) {
                    htmlContent += '<div>' +
                        '<h3>' + recipe.label + '</h3>' +
                        '<img src="' + recipe.image + '" alt="' + recipe.label + '">' +
                        '<p>URL: <a href="' + recipe.url + '" target="_blank">' + recipe.url + '</a></p>' +
                        '<p>Ingredients: ' + recipe.ingredientLines.join(", ") + '</p>' +
                        '</div>';
                });
                $('#resultsContainer').html(htmlContent).show();
            },
            error: function(xhr, status, error) {
                console.error("Error: " + status + " - " + error);
            }
        });
    });
});