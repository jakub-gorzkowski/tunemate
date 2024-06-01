import axios from "axios";
import authenticationHeader from "../header/AuthenticationHeader";

const API_URL = "http://localhost:8080/api/playlists/";

const getHomePage = () => {
    return axios.get(API_URL + "homepage", { headers: authenticationHeader() });
};

const PlaylistService = {
    getUserBoard
};

export default PlaylistService;