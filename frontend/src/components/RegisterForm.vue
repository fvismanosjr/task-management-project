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
import { register } from "@/services/auth"
import { useRouter } from 'vue-router';
import { ref } from "vue"

const router = useRouter();
const registerForm = ref({
    username: "",
    password: "",
    confirmPassword: "",
})

const registerUser = async () => {
    const response = await register(registerForm.value);

    if (response.ok) {
        router.push({
            name: "login",
        });
    }
}

</script>

<template>
    <div class="flex flex-col gap-6">
        <Card>
            <CardHeader class="text-center">
                <CardTitle class="text-xl">
                    Create an account
                </CardTitle>
                <CardDescription>
                    Enter your information below to create your account
                </CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <FieldGroup>
                        <Field>
                            <FieldLabel for="username">
                                Username
                            </FieldLabel>
                            <Input v-model="registerForm.username" id="username" type="text" placeholder="username" required />
                        </Field>
                        <Field>
                            <FieldLabel for="password">
                                Password
                            </FieldLabel>
                            <Input v-model="registerForm.password" id="password" type="password" required />
                        </Field>
                        <Field>
                            <FieldLabel for="confirm">
                                Confirm Password
                            </FieldLabel>
                            <Input v-model="registerForm.confirmPassword" id="confirm" type="password" required />
                        </Field>
                        <Field>
                            <Button type="submit" @click.prevent="registerUser">
                                Register
                            </Button>
                            <FieldDescription class="text-center">
                                Already have an account?
                                <a href="/login">
                                    Sign in
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
