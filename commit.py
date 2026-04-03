#!/usr/bin/env python3
import subprocess, os

os.chdir('/Users/nouhaila/Downloads/avis_clean')

# Désindexer stage.py
subprocess.run(['git', 'reset', 'HEAD', 'stage.py'], capture_output=True)

msg = """feat: endpoints complets Swagger + UtilisateurUseCase

Jeu: PUT /{id} (modifier), DELETE /{id} (supprimer)
Avis: GET /{id}, DELETE /{id} (JOUEUR/MODERATEUR)
Utilisateurs: GET/PUT/DELETE joueurs, GET moderateurs
Architecture: UtilisateurUseCase, UtilisateurInteractor,
findById/findAll/deleteById dans les ports out et adapters JPA,
ModerateurResponse, ModifierJoueurRequest, BeanConfig mis a jour"""

r = subprocess.run(['git', 'commit', '-m', msg], capture_output=True, text=True)
print(r.stdout)
print(r.stderr)

r = subprocess.run(['git', 'push'], capture_output=True, text=True)
print(r.stdout)
print(r.stderr)

