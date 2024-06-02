import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Search() {
    const [searchText, setSearchText] = useState('');
    const navigate = useNavigate();

    const handleSearch = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/artists/get/artist-id/${encodeURIComponent(searchText)}`);
            const artistId = response.data;
            navigate(`/artist/${artistId}`);
        } catch (error) {
        }
    };

    const handleChange = (event) => {
        setSearchText(event.target.value);
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSearch();
        }
    };

    return (
        <input
            name="searchbar"
            placeholder={"Type to search..."}
            className={[
                "w-search",
                "h-search",
                "bg-search",
                "text-menu",
                "rounded-lg",
                "fixed",
                "mt-10",
                "p-4",
                "opacity-90"
            ].join(' ')}
            value={searchText}
            onChange={handleChange}
            onKeyPress={handleKeyPress}
        />
    );
}

export default Search;
