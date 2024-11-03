import React, { useState } from 'react';

export default function UploadDocument() {
    const [author, setAuthor] = useState('');
    const [title, setTitle] = useState('');
    const [file, setFile] = useState(null);
    const [response, setResponse] = useState(null); // To display responses from API calls

    const baseURL = 'http://localhost:8080/api';

    // Upload a new document
    const uploadDocument = async () => {
        if (!author || !title || !file) {
            alert("Please fill in all fields and select a file.");
            return;
        }

        const formData = new FormData();
        formData.append("author", author);
        formData.append("title", title);
        formData.append("file", file);

        try {
            const res = await fetch(`${baseURL}/document`, {
                method: 'POST',
                body: formData,
            });

            if (!res.ok) {
                throw new Error("Error uploading document");
            }

            const data = await res.json();
            setResponse(data);
        } catch (error) {
            console.error(error);
            setResponse({ error: error.message });
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
            <div className="response-container">
                <h3>Response</h3>
                <pre>{JSON.stringify(response, null, 2)}</pre>
            </div>
        </div>
    );
}
