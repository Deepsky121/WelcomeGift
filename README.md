# 🪙 WelcomeGift

**WelcomeGift** is a simple Spigot/Paper plugin that automatically gives players a reward when they first join the server.  
Admins can also manually give rewards, reset players’ reward status, and reload the configuration.  

---

## ✨ Features
- Automatic reward on first join.
- Fully configurable rewards in `config.yml` (supports name, lore, enchantments).
- Admin commands:
  - `/welcomegift rewards <player>` → give rewards manually.
  - `/welcomegift reset <player>` → reset a player's reward status (they get it again when rejoining).
  - `/welcomegift reload` → reload the configuration without restarting the server.
- Tab-complete for subcommands and online player names.
- Player data stored in `data.yml`.

---

## 📂 Project Structure
