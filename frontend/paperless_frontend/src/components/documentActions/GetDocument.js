import React, { useState } from 'react';

export default function GetDocument() {
    const [id, setId] = useState('');
    const [isFetchingAll, setIsFetchingAll] = useState(false);

    const fetchDocument = () => {
        // Implement the logic to fetch the document by ID
        console.log(`Fetching document with ID: ${id}`);
        // Add your fetch logic here
    };

    const fetchAllDocuments = () => {
        // Implement the logic to fetch all documents
        console.log('Fetching all documents');
        // Add your fetch logic here
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
                <button onClick={fetchDocument}>Fetch Document</button>
            </div>

            <div className="get-all-documents">
                <h3>Get All Documents</h3>
                <button onClick={fetchAllDocuments}>Fetch All Documents</button>
            </div>
        </div>
    );
}
