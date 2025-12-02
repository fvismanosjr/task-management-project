<script setup lang="ts">
import {
    Table,
    TableBody,
    // TableCaption,
    TableCell,
    // TableFooter,
    TableHead,
    TableHeader,
    TableRow,
} from '@/components/ui/table'

import { formatNumberToHuman, formatNumberToPercentage } from '@/helpers/Number';

import { getAssets } from '@/services/asset';
import { ref } from 'vue';

const assets = ref();

getAssets().then((response) => {
    assets.value = response;
});
</script>

<template>
    <Table>
        <TableHeader>
            <TableRow>
                <TableHead class="w-[50px]">Rank</TableHead>
                <TableHead class="w-[100px]">Name</TableHead>
                <TableHead>Price</TableHead>
                <TableHead>Market Cap</TableHead>
                <TableHead>VWAP(24Hr)</TableHead>
                <TableHead>Supply</TableHead>
                <TableHead>Volume(24Hr)</TableHead>
                <TableHead>Change(24Hr)</TableHead>
            </TableRow>
        </TableHeader>
        <TableBody>
            <TableRow v-for="asset in assets" :key="asset.id">
                <TableCell>{{ asset.rank }}</TableCell>
                <TableCell>{{ asset.name }}</TableCell>
                <TableCell>${{ formatNumberToHuman(asset.priceUsd) }}</TableCell>
                <TableCell>${{ formatNumberToHuman(asset.marketCapUsd) }}</TableCell>
                <TableCell>${{ formatNumberToHuman(asset.vwap24Hr) }}</TableCell>
                <TableCell>${{ formatNumberToHuman(asset.supply) }}</TableCell>
                <TableCell>${{ formatNumberToHuman(asset.volumeUsd24Hr) }}</TableCell>
                <TableCell>{{ formatNumberToPercentage(asset.changePercent24Hr) }}</TableCell>
            </TableRow>
        </TableBody>
    </Table>
</template>
