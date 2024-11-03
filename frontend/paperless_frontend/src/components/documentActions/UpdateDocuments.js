// src/components/UpdateDocument.js
import React, { useState } from 'react';

export default function UpdateDocument() {
    const [id, setId] = useState('');
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [file, setFile] = useState(null);

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleUpdate = () => {
        // Placeholder for update logic
        console.log(`Updating document ID: ${id}`);
        console.log(`New Title: ${title}`);
        console.log(`New Author: ${author}`);
        if (file) {
            console.log(`New File: ${file.name}`);
        }
    };

    return (
        <div className="action">
            <h3>Update Document</h3>
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
                onChange={handleFileChange}
            />
            <button onClick={handleUpdate}>Update Document</button>
        </div>
    );
}
