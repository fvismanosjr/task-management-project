export const ucwords = (str: string) => {
    return String(str)
            .toLowerCase()
            .replace(/\b[a-z]/g, (letter) => letter.toUpperCase());
}

export const initials = (str: string, min: number = 1) => {
    return String(str)
            .trim()
            .split(/\s+/)
            .filter(word => word.length > min && /^[A-Za-z]/.test(word))
            .map(w => w[0]?.toUpperCase())
            .join("");
}
