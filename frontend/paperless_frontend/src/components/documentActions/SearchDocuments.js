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
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search Query"
            />
            <button onClick={handleSearch}>Search</button>
        </div>
    );
}
