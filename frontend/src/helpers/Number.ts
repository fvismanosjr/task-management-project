export const formatNumberToHuman = (num: number) => {
    return new Intl.NumberFormat('en', {
        notation: "compact",
        compactDisplay: "short",
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(num);
}

export const formatNumberToPercentage = (num: number) => {
    return new Intl.NumberFormat('en', {
        style: "percent",
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(num);
}
