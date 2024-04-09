
function Search() {
    return (
        <input name="searchbar" placeholder={"Type to search..."} className={[
            "w-search",
            "h-search",
            "bg-search",
            "text-menu",
            "rounded-lg",
            "fixed",
            "mt-10",
            "p-4",
            "opacity-90"
        ].join(' ')}/>
    );
}

export default Search;