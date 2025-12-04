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

export interface TaskTypeResponseType {
    id: number;
    title: string;
}

export interface BoardResponseType {
    id: number,
    name: string,
    members: BoardMemberType[],
    tasks: TaskTypeResponseType[],
}
