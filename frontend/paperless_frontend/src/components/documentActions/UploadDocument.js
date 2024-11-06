import React, { useState } from 'react';

export default function UploadDocument() {
    const [author, setAuthor] = useState('');
    const [title, setTitle] = useState('');
    const [file, setFile] = useState(null);

    const baseURL = 'http://rest-api:8081/api';

    // Upload a new document
    const uploadDocument = async () => {
        const formData = new FormData();
        formData.append("author", author);
        formData.append("title", title);
        formData.append("file", file);

        try {
            const response = await fetch('http://localhost:8081/api/documents/post_document', {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Error: ${errorMessage}`);
            }

            console.log('Upload successful');
        } catch (error) {
            console.error('Upload failed:', error);
        }
    };

    return (
        <div className="action">
            <h3>Upload Document</h3>
            <input
                type="text"
                value={author}
                onChange={(e) => setAuthor(e.target.value)}
                placeholder="Author"
                required
            />
            <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Title"
                required
            />
            <input
                type="file"
                onChange={(e) => setFile(e.target.files[0])}
                required
            />
            <button onClick={uploadDocument}>Upload Document</button>
        </div>
    );
}
