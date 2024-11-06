import React, { useState } from 'react';

export default function DeleteDocument() {
    const [id, setId] = useState('');

    const handleDelete = async (id) => {

        try {
            const response = await fetch(`http://localhost:8081/api/documents/${id}`, {
                method: 'DELETE',
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to delete document: ${errorMessage}`);
            }

            console.log(`Document with ID: ${id} deleted successfully.`);
        } catch (error) {
            console.error('Error deleting document:', error);
        }

    }

    return (
        <div className="action">
            <h3>Delete Document</h3>
            <input
                type="text"
                value={id}
                onChange={(e) => setId(e.target.value)}
                placeholder="Document ID"
            />
            <button onClick={() => handleDelete(id)}>Delete Document</button>
        </div>

    );
}