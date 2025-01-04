import React, { useState } from 'react';

export default function SearchDocuments() {
    const [searchQuery, setSearchQuery] = useState('');

    const handleSearch = () => {
        console.log(`Searching for documents with query: ${searchQuery}`);
    };

    return (
        <div className="action">
            <h3>Search Documents</h3>
            <input
                id="title"
                type="text"
                value={searchQuery.title}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search by Title"
            />
            <input
                id="author"
                type="text"
                value={searchQuery.author}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search by Author"
            />
            <input
                id="content"
                type="text"
                value={searchQuery.content}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search by Content"
            />
            <button onClick={handleSearch}>Search</button>
        </div>
    );
}
