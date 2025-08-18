<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="css/output.css" rel="stylesheet">
<title>Se connecter</title>
</head>
<body>
<div class="flex items-center justify-center min-h-screen bg-gradient-to-br from-[#011222] to-[#4070f4] p-4">
    <div class="max-w-[400px] w-full bg-white rounded-lg shadow-lg overflow-hidden">
        <div class="h-[70px] bg-[#4070f4] rounded-t-lg flex items-center justify-center">
            <span class="text-white text-3xl font-semibold">Connexion</span>
        </div>
        <form class="p-6" action="login" method="post">
            <div class="relative mt-4">
                <div class="absolute inset-y-0 left-0 flex items-center pl-2 pr-2 bg-[#4070f4] rounded-l-lg">
                    <!-- Utilisez une icône ou un élément SVG ici pour remplacer <User /> -->
                    <svg class="text-white w-6 h-6" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 12c2.485 0 4.5-1.985 4.5-4.5S14.485 3 12 3 7.5 4.985 7.5 7.5 9.515 12 12 12zm0 1.5c-2.207 0-6 1.144-6 3.428V19h12v-2.072c0-2.284-3.793-3.428-6-3.428z"/></svg>
                </div>
                <input
                    type="email"
                    name="email"
                    placeholder="Adresse email"
                    class="w-full pl-16 pr-4 py-3 rounded-lg border border-gray-300 focus:border-[#4070f4] focus:outline-none text-lg transition-colors"
                    required
                 />
            </div>
            <div class="relative mt-4">
                <div class="absolute inset-y-0 left-0 flex items-center pl-2 pr-2 bg-[#4070f4] rounded-l-lg">
                    <!-- Utilisez une icône ou un élément SVG ici pour remplacer <Lock /> -->
                    <svg class="text-white w-6 h-6" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 2c-2.21 0-4 1.79-4 4v4H8v2h8V10h-1V6c0-2.21-1.79-4-4-4zM4 10v6h16v-6H4z"/></svg>
                </div>
                <input
                    type="password"
                    name="password"
                    placeholder="Mot de passe"
                    class="w-full pl-16 pr-4 py-3 rounded-lg border border-gray-300 focus:border-[#4070f4] focus:outline-none text-lg transition-colors"
                    required
                />
            </div>
            <div class="mt-3">
                <a href="#" class="text-[#4070f4] text-lg hover:underline">
                    Mot de passe oublié?
                </a>
            </div>
            <div class="mt-6">
                <input
                    type="submit"
                    value="Se connecter"
                    class="w-full py-3 bg-[#4070f4] text-white text-xl font-semibold rounded-lg cursor-pointer hover:bg-[#052b85] transition-colors"
                />
            </div>

            <div class="mt-8 text-center text-lg">
                <span> Pas de compte? </span>
                <a href="#" class="text-[#4070f4] hover:underline">
                    S'inscrire
                </a>
            </div>
        </form>
    </div>
</div>

</body>
</html>