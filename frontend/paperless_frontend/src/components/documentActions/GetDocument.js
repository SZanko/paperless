import React, { useState } from 'react';

export default function GetDocument() {
    const [id, setId] = useState('');

    const fetchDocument = async (id) => {

        try{
            const response = await fetch(`http://localhost:8081/api/documents/${id}`,
                {method: 'GET',
        });
            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to fetch document: ${errorMessage}`);
            }

            const documentData = await response.json();
            console.log('Document fetched successfully:', documentData);

            return documentData;}
        catch (error){
            console.error('Error fetching document:', error);
        }
        console.log(`Fetching document with ID: ${id}`);
    };

    const fetchAllDocuments = async () => {
        console.log('Fetching all documents');

        try {
            const response = await fetch('http://localhost:8081/api/documents', {
                method: 'GET',
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(`Failed to fetch documents: ${errorMessage}`);
            }

            const documentsList = await response.json();
            console.log('All documents fetched successfully:', documentsList);

            return documentsList;
        } catch (error) {
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
                <button onClick={() => fetchDocument(id)}>Fetch Document</button>
            </div>

            <div className="get-all-documents">
                <h3>Get All Documents</h3>
                <button onClick={fetchAllDocuments}>Fetch All Documents</button>
            </div>
        </div>
    );
}
