import React, { useState } from 'react';
import { BASE_URL } from '../../config';
import "./uploadDocuments.css";


export default function UploadDocument() {
    const [author, setAuthor] = useState('');
    const [title, setTitle] = useState('');
    const [file, setFile] = useState(null);
    const [showForm, setShowForm] = useState(false);

    const uploadDocument = async () => {
        const formData = new FormData();
        formData.append("author", author);
        formData.append("title", title);
        formData.append("file", file);

        try {
            const response = await fetch(`${BASE_URL}/documents/post_document`, {
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
            {/* Button to show the form */}
            {!showForm ? (
                <button onClick={() => setShowForm(true)}>Upload New Document</button>
            ) : (
                <div className="upload-form">
                    {/* Fields to enter document details */}
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
                    <button onClick={() => setShowForm(false)}>Cancel</button>
                </div>
            )}
        </div>
    );
}
