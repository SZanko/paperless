import React, { useState } from 'react';
import { BASE_URL } from '../../config';
import "./getDocuments.css";

export default function GetDocument() {
    const [id, setId] = useState('');
    const [documentData, setDocumentData] = useState(null);
    const [documentsList, setDocumentsList] = useState([]);
    const [error, setError] = useState(null);

    const fetchDocument = async (id) => {
        setError(null);
        try {
            const response = await fetch(`${BASE_URL}/documents/${id}`, {
                method: 'GET',
            });
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to fetch document: ${errorMessage}`);
            }
            const document = await response.json();
            setDocumentData(document);
        } catch (error) {
            setError(error.message);
        }
    };

    const fetchAllDocuments = async () => {
        setError(null);
        try {
            const response = await fetch(`${BASE_URL}/documents`, {
                method: 'GET',
            });
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to fetch documents: ${errorMessage}`);
            }
            const documents = await response.json();
            setDocumentsList(documents);
        } catch (error) {
            setError(error.message);
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
                <div className="buttons-container">
                    <button onClick={() => fetchDocument(id)} className="button">Fetch Document</button>
                    <button onClick={fetchAllDocuments} className="button">Fetch All Documents</button>
                </div>
                {documentData && (
                    <div className="document-details">
                        <h4>Document Details:</h4>
                        <table>
                            <thead>
                            <tr>
                                {Object.keys(documentData).map((key) => (
                                    <th key={key}>{key}</th>
                                ))}
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                {Object.entries(documentData).map(([key, value], index) => (
                                    <td key={index}>{key === 'content' ? JSON.stringify(value).slice(0, 100) + '...' : JSON.stringify(value)}</td>
                                ))}
                            </tr>
                            </tbody>
                        </table>
                    </div>
                )}

                {documentsList.length > 0 && (
                    <div className="documents-list">
                        <h4>All Documents:</h4>
                        <table>
                            <thead>
                            <tr>
                                {Object.keys(documentsList[0]).map((key) => (
                                    <th key={key}>{key}</th>
                                ))}
                            </tr>
                            </thead>
                            <tbody>
                            {documentsList.map((doc, index) => (
                                <tr key={index}>
                                    {Object.entries(doc).map(([key, value], idx) => (
                                        <td key={idx}>{key === 'content' ? JSON.stringify(value).slice(0, 100) + '...' : JSON.stringify(value)}</td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                )}
                {error && <div className="error-message">Error: {error}</div>}
            </div>
        </div>
    );
}
