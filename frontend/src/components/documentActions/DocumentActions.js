// src/components/DocumentActions.js
import React, { useState } from 'react';
import './DocumentActions.css';
import UploadDocument from "./UploadDocument";
import GetDocument from "./GetDocument";
import DeleteDocument from "./DeleteDocument";
import UpdateDocument from "./UpdateDocuments";
import SearchDocuments from "./SearchDocuments";

export default function DocumentActions() {
    const [searchQuery, setSearchQuery] = useState('');

    return (
        <div className="document-actions-container">
            <h2>Document Actions</h2>

            <UploadDocument />
            <GetDocument />
            <DeleteDocument />
            <UpdateDocument />
            <SearchDocuments />
        </div>
    );
}
