// src/components/Dashboard.js
import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import UploadDocument from "../documentActions/UploadDocument";
import GetDocument from "../documentActions/GetDocument";
import DeleteDocument from "../documentActions/DeleteDocument";
import UpdateDocumentMetadata from "../documentActions/UpdateDocuments";
import SearchDocuments from "../documentActions/SearchDocuments";


export default function Dashboard() {
    const [message, setMessage] = useState('Loading...');

    useEffect(() => {
        fetch('/api/hello-world')
            .then(response => response.json())
            .then(data => setMessage(data.message))
            .catch(err => setMessage('Error fetching message'));
    }, []);

    return (
        <div className="screen">
            <div className="uploadButton">
                <UploadDocument/>
            </div>
            <div className="dashboard-box">
                <h1>Dashboard</h1>
                <div className="fetchDelete">
                    <GetDocument/>
                    <DeleteDocument/>
                </div>
                <div className="searchDocument">
                    <SearchDocuments/>
                </div>
                <div className="updateDocument">
                    <UpdateDocumentMetadata/>
                </div>
            </div>
        </div>
    );
}
