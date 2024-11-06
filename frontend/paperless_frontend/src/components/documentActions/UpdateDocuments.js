import React, { useState } from 'react';

export default function UpdateDocumentMetadata() {
    const [id, setId] = useState('');
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [file, setFile] = useState(null);

    // API endpoint base URL
    const baseURL = 'http://localhost:8081/api';

    const updateMetadata = async () => {
        const formData = new FormData();
        formData.append('title', title);
        if (author) formData.append('author', author);
        if (file) formData.append('file', file);

        try {
            const response = await fetch(`${baseURL}/documents/${id}`, {
                method: 'PUT',
                body: formData,
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Error: ${errorMessage}`);
            }

            const updatedDocument = await response.json();
            console.log('Document updated successfully:', updatedDocument);

        } catch (error) {
            console.error('Error updating document:', error);
        }
    };

    return (
        <div className="action">
            <h3>Update Document Metadata</h3>

            <input
                type="text"
                value={id}
                onChange={(e) => setId(e.target.value)}
                placeholder="Document ID"
            />
            <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="New Title"
            />
            <input
                type="text"
                value={author}
                onChange={(e) => setAuthor(e.target.value)}
                placeholder="New Author"
            />
            <input
                type="file"
                onChange={(e) => setFile(e.target.files[0])}
            />
            <button onClick={updateMetadata}>Update Document Metadata</button>
        </div>
    );
}
