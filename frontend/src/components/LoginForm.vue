<script setup lang="ts">
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import {
    Field,
    FieldDescription,
    FieldGroup,
    FieldLabel,
} from "@/components/ui/field"

import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { login } from "@/services/auth"
import { useRouter } from 'vue-router';
import { useUserStore } from "@/stores/user"
import { ref } from "vue"

const user = useUserStore();
const router = useRouter();

const loginForm = ref({
    username: "",
    password: "",
});

const loginUser = async () => {
    const response = await login(loginForm.value);

    if (response.ok) {
        const result = await response.json();
        const payload = {
            id: result.id,
            username: result.username,
            role: result.role,
        }

        user.save(payload);

        router.push({
            name: "board",
        });
    }
}
</script>

<template>
    <div class="flex flex-col gap-6">
        <Card>
            <CardHeader class="text-center">
                <CardTitle class="text-xl">
                    Welcome back
                </CardTitle>
                <CardDescription>
                    Login with your email account
                </CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <FieldGroup>
                        <Field>
                            <FieldLabel for="username">
                                Username
                            </FieldLabel>
                            <Input v-model="loginForm.username" id="username" type="text" placeholder="username" required />
                        </Field>
                        <Field>
                            <div class="flex items-center">
                                <FieldLabel for="password">
                                    Password
                                </FieldLabel>
                                <a href="#" class="ml-auto text-sm underline-offset-4 hover:underline" tabindex="-1">
                                    Forgot your password?
                                </a>
                            </div>
                            <Input v-model="loginForm.password" id="password" type="password" required />
                        </Field>
                        <Field>
                            <Button type="submit" @click.prevent="loginUser">
                                Login
                            </Button>
                            <FieldDescription class="text-center">
                                Don't have an account?
                                <a href="/register">
                                    Sign up
                                </a>
                            </FieldDescription>
                        </Field>
                    </FieldGroup>
                </form>
            </CardContent>
        </Card>
        <FieldDescription class="px-6 text-center">
            By clicking continue, you agree to our <a href="#">Terms of Service</a>
            and <a href="#">Privacy Policy</a>.
        </FieldDescription>
    </div>
</template>
