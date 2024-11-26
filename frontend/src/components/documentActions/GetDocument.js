import React, { useState } from 'react';
import { BASE_URL } from '../../config';
import "./getDocuments.css"

export default function GetDocument() {
    const [id, setId] = useState('');
    const [documentData, setDocumentData] = useState(null); // State to hold the fetched document data
    const [documentsList, setDocumentsList] = useState([]); // State to hold the list of all documents
    const [error, setError] = useState(null); // State to handle any errors

    const fetchDocument = async (id) => {
        setError(null); // Reset error before fetching
        try {
            const response = await fetch(`${BASE_URL}/documents/${id}`, {
                method: 'GET',
            });
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to fetch document: ${errorMessage}`);
            }

            const document = await response.json();
            setDocumentData(document); // Update the state with the fetched document data
            console.log('Document fetched successfully:', document);
        } catch (error) {
            setError(error.message); // Set the error if there's a failure
            console.error('Error fetching document:', error);
        }
    };

    const fetchAllDocuments = async () => {
        setError(null); // Reset error before fetching
        console.log('Fetching all documents');

        try {
            const response = await fetch(`${BASE_URL}/documents`, {
                method: 'GET',
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to fetch documents: ${errorMessage}`);
            }

            const documents = await response.json();
            setDocumentsList(documents); // Update the state with the fetched documents list
            console.log('All documents fetched successfully:', documents);
        } catch (error) {
            setError(error.message); // Set the error if there's a failure
            console.error('Error fetching documents:', error);
        }
    };

    return (
        <div className="action">
            <div className="get-document">
                <h3>Get Document</h3>
                <input
                    type="text"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                    placeholder="Document ID"
                />

                {/* Wrapper div for buttons */}
                <div className="buttons-container">
                    <button onClick={() => fetchDocument(id)} className="button">Fetch Document</button>
                    <button onClick={fetchAllDocuments} className="button">Fetch All Documents</button>
                </div>

                {documentData && (
                    <div className="document-details">
                        <h4>Document Details:</h4>
                        <pre>{JSON.stringify(documentData, null, 2)}</pre>
                    </div>
                )}

                {error && <div className="error-message">Error: {error}</div>}
            </div>

            {documentsList.length > 0 && (
                <div className="documents-list">
                    <h4>All Documents:</h4>
                    <pre>{JSON.stringify(documentsList, null, 2)}</pre>
                </div>
            )}

            {error && <div className="error-message">Error: {error}</div>}
        </div>
    );
}
