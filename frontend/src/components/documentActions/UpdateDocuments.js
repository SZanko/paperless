import React, { useState } from 'react';
import { BASE_URL } from '../../config';

export default function UpdateDocumentMetadata() {
    const [id, setId] = useState('');
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [message, setMessage] = useState('');
    const [isError, setIsError] = useState(false);


    const updateMetadata = async () => {
        // Input validation
        if (!id || !title) {
            setMessage('Document ID and Title are required');
            setIsError(true);
            return;
        }

        const formData = new FormData();
        formData.append('title', title);
        if (author) formData.append('author', author);

        try {
            setMessage('Updating document...');
            setIsError(false);

            const response = await fetch(`${BASE_URL}/documents/${id}`, {
                method: 'PUT',
                body: formData,
            });

            if (!response.ok) {
                throw new Error(
                    response.status === 404
                        ? 'Document not found'
                        : 'Failed to update document'
                );
            }

            const updatedDocument = await response.json();
            setMessage('Document updated successfully!');
            console.log('Document updated successfully:', updatedDocument);

            // Clear form after successful update
            setTitle('');
            setAuthor('');
            setId('');

        } catch (error) {
            setMessage(error.message);
            setIsError(true);
            console.error('Error updating document:', error);
        }
    };

    return (
        <div className="action">
            <h3>Update Document Metadata</h3>

            {message && (
                <div className={`message ${isError ? 'error' : 'success'}`}>
                    {message}
                </div>
            )}

            <div className="input-group">
                <input
                    type="text"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                    placeholder="Document ID (required)"
                    className="form-input"
                />
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="New Title (required)"
                    className="form-input"
                />
                <input
                    type="text"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    placeholder="New Author (optional)"
                    className="form-input"
                />
                <button
                    onClick={updateMetadata}
                    className="update-button"
                    disabled={!id || !title}
                >
                    Update Document Metadata
                </button>
            </div>
        </div>
    );
}
