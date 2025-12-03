const BOARD_API_URL = "http://localhost:8080/board-members";

export const getUserMembers = async () => {
    return await fetch(BOARD_API_URL, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}
