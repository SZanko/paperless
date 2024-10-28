// src/components/DocumentActions.js
import React, { useState } from 'react';
import './DocumentActions.css';


export default function DocumentActions() {
    const [id, setId] = useState('');
    const [author, setAuthor] = useState('');
    const [title, setTitle] = useState('');
    const [file, setFile] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');

    return (
        <div>


            <div className="action">
                <h3>Get All Documents</h3>
                <button>Fetch All Documents</button>
            </div>

            <div className="action">
                <h3>Delete Document</h3>
                <input
                    type="text"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                    placeholder="Document ID"
                />
                <button>Delete Document</button>
            </div>

            <div className="action">
                <h3>Create Document</h3>
                <input
                    type="text"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    placeholder="Author"
                />
                <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="Title"
                />
                <input
                    type="file"
                    onChange={(e) => setFile(e.target.files[0])}
                />
                <button>Upload Document</button>
            </div>


            <div className="action">
                <h3>Search Documents</h3>
                <input
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Search Query"
                />
                <button>Search</button>
            </div>
        </div>
    );
}
