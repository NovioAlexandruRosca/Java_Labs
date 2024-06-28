document.getElementById('renameBookButton').addEventListener('click', renameBook);
document.getElementById('addBookButton').addEventListener('click', addBook);
document.getElementById('deleteBookButton').addEventListener('click', deleteBook);
document.getElementById('listBooksButton').addEventListener('click', listBooks);
document.getElementById('listAuthorsButton').addEventListener('click', listAuthors);

const output = document.getElementById('output');

async function renameBook() {
    const bookId = document.getElementById('renameBookId').value;
    const newName = document.getElementById('newBookName').value;

    const response = await fetch(`http://localhost:9090/api/books/${bookId}/name`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: newName })
    });

    if (response.ok) {
        output.textContent = 'Book renamed successfully!';
    } else {
        output.textContent = 'Failed to rename book.';
    }
}

async function addBook() {
    const title = document.getElementById('bookTitle').value;
    const language = document.getElementById('bookLanguage').value;
    const publicationDate = document.getElementById('publicationDate').value;
    const numPages = document.getElementById('numPages').value;

    const response = await fetch('http://localhost:9090/api/books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title,
            language,
            publicationDate,
            numPages
        })
    });

    if (response.ok) {
        output.textContent = 'Book added successfully!';
    } else {
        output.textContent = 'Failed to add book.';
    }
}

async function deleteBook() {
    const bookId = document.getElementById('deleteBookId').value;

    const response = await fetch(`http://localhost:9090/api/books/${bookId}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        output.textContent = 'Book deleted successfully!';
    } else {
        output.textContent = 'Failed to delete book.';
    }
}

async function listBooks() {
    const response = await fetch('http://localhost:9090/api/books');
    if (response.ok) {
        const books = await response.json();
        output.textContent = JSON.stringify(books, null, 2);
    } else {
        output.textContent = 'Failed to retrieve books.';
    }
}

async function listAuthors() {
    const response = await fetch('http://localhost:9090/api/authors');
    if (response.ok) {
        const authors = await response.json();
        output.textContent = JSON.stringify(authors, null, 2);
    } else {
        output.textContent = 'Failed to retrieve authors.';
    }
}
