import requests
from requests.auth import HTTPBasicAuth


def trigger_jenkins_build():
    # Configuration
    base_url = "http://localhost:8080"
    job_name = "jenkinstest"
    token = "1010"
    username = "amoros"
    password = "admin123"

    # Créer une session pour maintenir les cookies
    session = requests.Session()
    session.auth = (username, password)

    try:
        print("🔐 Récupération du crumb CSRF...")

        # Récupérer le crumb
        crumb_response = session.get(f"{base_url}/crumbIssuer/api/json")

        if crumb_response.status_code == 200:
            crumb_data = crumb_response.json()
            crumb = crumb_data["crumb"]
            crumb_field = crumb_data["crumbRequestField"]

            print(f"✅ Crumb récupéré: {crumb}")
            print(f"📋 Crumb field: {crumb_field}")

            # Étape 2: Déclencher le build avec le crumb dans les headers
            build_url = f"{base_url}/job/{job_name}/build?token={token}"

            # Les headers doivent inclure le champ spécifique (généralement 'Jenkins-Crumb')
            headers = {
                crumb_field: crumb,
                "Content-Type": "application/x-www-form-urlencoded",
            }

            print("🚀 Déclenchement du build...")
            print(f"📤 URL: {build_url}")
            print(f"📨 Headers: {headers}")

            # Important: envoyer des données vides en POST
            build_response = session.post(
                build_url,
                headers=headers,
                data={},  # Données POST vides mais nécessaires
            )

            print(f"📊 Status Code: {build_response.status_code}")

            if build_response.status_code in [200, 201, 302]:
                print("✅✅ Build déclenché avec succès!")
                return True
            else:
                print(f"❌ Erreur lors du build")
                print(f"📄 Réponse: {build_response.text}")
                return False

        else:
            print(f"❌ Erreur crumb: {crumb_response.status_code}")
            print(f"📄 Réponse crumb: {crumb_response.text}")
            return False

    except Exception as e:
        print(f"❌ Exception: {e}")
        import traceback

        traceback.print_exc()
        return False


# Exécuter la fonction
if __name__ == "__main__":
    trigger_jenkins_build()
