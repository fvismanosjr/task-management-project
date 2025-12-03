export interface BoardType {
    id: number;
    name: string;
}

export interface BoardWithMembersType {
    id: number;
    name: string;
    members: string[];
}

export interface TaskType {
    id: number;
    title: string;
    boardId: number;
}

export interface BoardMemberType {
    id: number;
    username: string;
}
