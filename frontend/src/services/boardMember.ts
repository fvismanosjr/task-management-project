const BOARD_MEMBER_API_URL = "http://localhost:8080/board-members";

export const getUserMembers = async () => {
    return await fetch(BOARD_MEMBER_API_URL, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const findMember = async (id: number) => {
    return await fetch(`${BOARD_MEMBER_API_URL}/${id}`, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}
