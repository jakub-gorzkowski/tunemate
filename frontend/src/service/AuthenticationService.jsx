import axios from "axios";

const API_URL = "http://localhost:8080/api/authentication/";

const register = (username, email, password) => {
    return axios.post(API_URL + "register", {
        username,
        email,
        password,
    });
};

const login = (email, password) => {
    return axios
        .post(API_URL + "authenticate", {
            email,
            password,
        })
        .then((response) => {
            if (response.data.jwtToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }

            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const AuthenticationService = {
    register,
    login,
    logout,
    getCurrentUser,
};

export default AuthenticationService;