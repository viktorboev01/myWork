import random

class Card:
    def __init__(self, suit, face):
        self._suit = suit
        self._face = face

    def get_suit(self):
        return self._suit

    def get_face(self):
        return self._face

class Deck:
    def __init__(self, cards):
        self._cards = []
        for card in cards:
            for suit in ("clubs", "diamonds", "hearts", "spades"):
                self._cards.append(Card(suit,card))
        
    def cut(self):
        cut_point = random.randrange(1, len(self._cards) - 1) 
        temp = self._cards[:cut_point]
        self._cards = self._cards[cut_point:]
        self._cards.extend(temp)
    
    def shuffle(self):
        random.shuffle(self._cards)

    def get_cards(self):
        return self._cards
          
    def get_top_card(self):
        card = self._cards[len(self._cards) - 1]   
        self._cards.pop()
        return card

    def extend(self, cards):
        self._cards.extend(cards)

class Player:
    def __init__(self):
        self._hand = []

    def get_cards(self):
        return self._hand

    def add_cards(self, list_cards):    
        self._hand.extend(list_cards)

    def return_cards(self):
        self._hand.clear()

class Game:
    def __init__(self, number_of_players, dealing_direction, dealing_instructions):
        self._players = []

        for i in range (number_of_players):
            self._players.append(Player())

        self._deal_dir = dealing_direction
        self._deal_inst = dealing_instructions
        self._deck = Deck(['2','3','4','5','6','7','8','9','10','J','Q','K','A'])

    def get_players(self):
        return self._players

    def prepare_deck(self):
        self.collect_all_cards()
        self._deck.shuffle()
        self._deck.cut()
        for player in self._players:
            player.return_cards()

    def get_n_cards_from_deck(self, n):
        cards_to_add = []
        for i in range(n):
            cards_to_add.append(self._deck.get_top_card())

        return cards_to_add

    def deal(self, player):

        for i, player_i in enumerate(self._players):
            if player_i is player:
                
                if self._deal_dir == "ltr":
                    for cnt_instr, instruction in enumerate(self._deal_inst) :

                        for cnt_pl, player_j in enumerate(self._players[i:]):
                            player_j.add_cards(self.get_n_cards_from_deck(instruction))

                        for cnt_pl, player_j in enumerate(self._players[:i]):
                            player_j.add_cards(self.get_n_cards_from_deck(instruction))

                else:
                    for cnt_instr, instruction in enumerate(self._deal_inst):

                        for cnt_pl, player_j in reversed(list(enumerate (self._players[:i]))):
                            player_j.add_cards(self.get_n_cards_from_deck(instruction))

                        for cnt_pl, player_j in reversed(list(enumerate (self._players[i:]))):
                            player_j.add_cards(self.get_n_cards_from_deck(instruction))

         
    def get_deck(self):
        return self._deck         

    def collect_all_cards(self):
        for pl in self._players:
            self._deck.extend(pl.get_cards())
            pl.return_cards()
    
class Belot(Game):
    
    def __init__(self):
        Game.__init__(self, 4, "ltr", (2, 3, 3))
        self._deck = Deck(['7','8','9','10','J','Q','K','A'])

class Poker(Game): 

    def __init__(self):
        Game.__init__(self, 9, "rtl", (1, 1, 1, 1, 1)) 
