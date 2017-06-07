package com.xt.landlords.ai.util.complex;

import java.util.*;

public class AISearch {

	private GameStatusAdv statusAdv;
	private int curPlayer;

	public AISearch(GameStatusAdv statusAdv) {
		// TODO 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
		this.statusAdv = statusAdv;
		this.curPlayer = statusAdv.getCurPlayer();
	}

	public AISearch() {
		//
	}

	private TreeNode node;
	public int nodeNumber = 0;
	private final int maxNodeNumber = 2000000;
	private int maxBombCount = 0;

	public NewHand SearchGoodMove() {
		nodeNumber = 0;
		int outPlayer = statusAdv.getOutHandPlayer();
		Hand outHand = statusAdv.getOutHand();
		int nowPlayer = curPlayer;
		List<Hand> MoveList;
		byte[][] playerCards = new byte[3][];
		for (int i = 0; i < 3; i++)
			playerCards[i] = statusAdv.getPlayerCards()[i].getCards();
		if (outPlayer == nowPlayer) {
			if (nowPlayer == 1)
				MoveList = DownPlayerMovesSort(null, playerCards[1], playerCards[2]);
			else
				MoveList = GetMoves(null, playerCards[nowPlayer]);
		} else {
			if (nowPlayer == 1)
				MoveList = DownPlayerMovesSort(outHand, playerCards[1], playerCards[2]);
			else
				MoveList = GetMoves(outHand, playerCards[nowPlayer]);
		}
		node = new TreeNode(nowPlayer, outPlayer, outHand, playerCards, 0);
		for (Hand hand : MoveList) {
			MakeMove(hand, node);
			nodeNumber++;
			boolean temp = IsMustWin(0);
			boolean reslut = node.getCurPlayer() == 2 ? temp : !temp;

			if (reslut) {
				if (nodeNumber > maxNodeNumber) {
					return null;
				}
				// 濡傛灉涓嶆槸鍦颁富璧㈢墝
				// if (node.getCurPlayer() != 0) {
				NewHand newHand = new NewHand(true, hand);
				newHand.setBombCount(maxBombCount);
				return newHand;
				// }
			}
			// else {
			// if (nodeNumber > maxNodeNumber) {
			// return null;
			// }
			// // 濡傛灉鏄湴涓昏緭鐗�
			// if (node.getCurPlayer() == 0) {
			// return new NewHand(true, hand);
			// }
			// }
			node = node.LastTreeNode;
		}
		NewHand nHand = new NewHand(false, null);
		return nHand;
	}

	public void SearchGoodMove(GameStatusAdv statusAdv) {
		int outPlayer = statusAdv.getOutHandPlayer();
		Hand outHand = statusAdv.getOutHand();
		int nowPlayer = statusAdv.getCurPlayer();
		List<Hand> MoveList;
		byte[][] playerCards = new byte[3][];
		for (int i = 0; i < 3; i++)
			playerCards[i] = statusAdv.getPlayerCards()[i].getCards();
		if (outPlayer == nowPlayer) {
			if (nowPlayer == 1)
				MoveList = DownPlayerMovesSort(null, playerCards[1], playerCards[2]);
			else
				MoveList = GetMoves(null, playerCards[nowPlayer]);
		} else {
			if (nowPlayer == 1)
				MoveList = DownPlayerMovesSort(outHand, playerCards[1], playerCards[2]);
			else
				MoveList = GetMoves(outHand, playerCards[nowPlayer]);
		}
		node = new TreeNode(nowPlayer, outPlayer, outHand, playerCards, 0);
		for (Hand hand : MoveList) {
			MakeMove(hand, node);
			if (IsMustWin2()) {
				// 濡傛灉涓嶆槸鍦颁富璧㈢墝
				if (node.getCurPlayer() != 0) {
					statusAdv.takeOut(hand);
				}
			} else {
				// 濡傛灉鏄湴涓昏緭鐗�
				if (node.getCurPlayer() == 0) {
					statusAdv.takeOut(hand);
				}
			}
			node = node.LastTreeNode;
		}
		statusAdv.takeOut(null);
	}

	private boolean IsMustWin(int depth) {
		int gameOver = IsGameOver(node.playerCards);
		int tmpCurPlayer = node.getCurPlayer();
		if (gameOver > -1) {
			maxBombCount = node.getBombCount();
			if ((gameOver > 0 && tmpCurPlayer > 0) || (gameOver == 0 && tmpCurPlayer == 0))
				return true;
			else
				return false;
		}
		List<Hand> MoveList;
		boolean value = false;
		// boolean isSide=false;
		// if(curPlayer==0&&tmpCurPlayer==0||tmpCurPlayer>0&&curPlayer>0)
		// isSide=true;
		if (node.comparePlayer()) {
			if (tmpCurPlayer == 1)
				MoveList = DownPlayerMovesSort(null, node.playerCards[1], node.playerCards[2]);
			else if (tmpCurPlayer == 0)
				MoveList = GetMovesDown(null, node.playerCards[tmpCurPlayer]);
			else
				MoveList = GetMoves(null, node.playerCards[tmpCurPlayer]);
		} else {
			if (tmpCurPlayer == 1)
				MoveList = DownPlayerMovesSort(node.getOutHand(), node.playerCards[1], node.playerCards[2]);
			else if (tmpCurPlayer == 0)
				MoveList = GetMovesDown(node.getOutHand(), node.playerCards[tmpCurPlayer]);
			else
				MoveList = GetMoves(node.getOutHand(), node.playerCards[tmpCurPlayer]);
		}
		for (Hand hand : MoveList) {
			MakeMove(hand, node);
			nodeNumber++;
			if (nodeNumber > maxNodeNumber) {
				return false;
			}
			if (node.getCurPlayer() == 2) {
				value = IsMustWin(depth + 1);
			} else {
				value = !IsMustWin(depth + 1);
			}
			node = node.LastTreeNode;
			// 找到一条赢得路径
			if (value) {
				return value;
			}
		}
		return value;
	}

	private boolean IsMustWin2() {
		int gameOver = IsGameOver(node.playerCards);
		int tmpCurPlayer = node.getCurPlayer();
		if (gameOver > -1) {
			if ((gameOver > 0 && tmpCurPlayer > 0) || (gameOver == 0 && tmpCurPlayer == 0))
				return true;
			else
				return false;
		}
		List<Hand> MoveList;
		boolean value = false;
		if (node.comparePlayer()) {
			if (tmpCurPlayer == 1)
				MoveList = DownPlayerMovesSort(null, node.playerCards[1], node.playerCards[2]);
			else if (tmpCurPlayer == 0)
				MoveList = GetMovesDown(null, node.playerCards[tmpCurPlayer]);
			else
				MoveList = GetMoves(null, node.playerCards[tmpCurPlayer]);
		} else {
			if (tmpCurPlayer == 1)
				MoveList = DownPlayerMovesSort(node.getOutHand(), node.playerCards[1], node.playerCards[2]);
			else if (tmpCurPlayer == 0)
				MoveList = GetMovesDown(node.getOutHand(), node.playerCards[tmpCurPlayer]);
			else
				MoveList = GetMoves(node.getOutHand(), node.playerCards[tmpCurPlayer]);
		}
		for (Hand hand : MoveList) {
			MakeMove(hand, node);
			if (node.getCurPlayer() == 2) {
				value = IsMustWin2();
			} else {
				value = !IsMustWin2();
			}
			node = node.LastTreeNode;
			// 找到一条赢得路径
			if (value) {
				return value;
			}
		}
		return value;
	}

	private int IsGameOver(byte[][] playerCards) {
		byte temp = 0;
		int player = 0;
		for (byte[] bs : playerCards) {
			for (byte b : bs) {
				temp |= b;
			}
			if (temp == 0)
				return player;
			else {
				player++;
				temp = 0;
			}
		}
		return -1;
	}

	private void MakeMove(Hand hand, TreeNode node) {
		int bombCount = node.getBombCount();
		node.LastTreeNode = node.saveNode();
		if (hand == null) {
			node.setCurPlayer((node.getCurPlayer() + 1) % 3);
			return;
		} else {
			byte[] handCards = AIUtils.handToCards(hand);
			// if (AIUtils.isBomb(hand)) {
			// node.setBombCount(node.getBombCount()+1);
			// }
			for (int i = 0; i < handCards.length; i++)
				node.playerCards[node.getCurPlayer()][i] -= handCards[i];
			if (hand.getType() == HandType.BOMB) {
				node.setBombCount(bombCount + 1);
			}
			node.setOutHand(hand);
			node.setOutPlyer(node.getCurPlayer());
			node.setCurPlayer((node.getCurPlayer() + 1) % 3);
		}
	}

	public static List<Hand> GetMoves(Hand hand, byte[] cards) {

		List<Hand> hands = new ArrayList<Hand>();
		List<Hand> BadHand = new ArrayList<Hand>();
		if (hand == null) {
			List<PokerType> listBomb = new ArrayList<PokerType>();
			List<Hand> listTrioChain = new ArrayList<Hand>();
			// SOLO_CHAIN
			for (int i = 0; i <= PokerType.TEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] != 0 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num > 4) {
						hand2 = new Hand(HandType.SOLO_CHAIN, PokerType.values()[i], num, null);
						hands.add(hand2);
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {

					hand2 = null;
				}
			}
			// PAIR_CHAIN
			for (int i = 0; i <= PokerType.QUEEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 1 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num > 2) {
						hand2 = new Hand(HandType.PAIR_CHAIN, PokerType.values()[i], num, null);
						hands.add(hand2);
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {

					hand2 = null;
				}
			}

			// TRIO_CHAIN
			for (int i = 0; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num > 1) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], num, null);
						listTrioChain.add(hand2);
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {

					hand2 = null;
				}
			}
			//
			// TRIO_CHAIN_SOLO
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() != 0) {
				for (Hand hand3 : listTrioChain) {
					int len = hand3.getLen();
					List<PokerType> listSolo = new ArrayList<PokerType>();
					int h_key = hand3.getKey().ordinal();
					// 鎵惧嚭鍓╀綑鏈夊崟涓�
					for (int i = 0; i <= PokerType.RJOKER.ordinal(); i++) {
						if (cards[i] > 0) {
							if (i >= h_key && i < h_key + len)
								continue;
							else {
								listSolo.add(PokerType.values()[i]);
							}
						}
					}
					if (listSolo.size() >= len) {
						switch (len) {
						case 2:
							for (int i = 0; i < listSolo.size() - 1; i++) {
								for (int j = i + 1; j < listSolo.size(); j++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listSolo.get(i));
									kickers.add(listSolo.get(j));
									hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));

								}
							}

							break;
						case 3:
							for (int i = 0; i < listSolo.size() - 2; i++) {
								for (int j = i + 1; j < listSolo.size() - 1; j++) {
									for (int x = j + 1; x < listSolo.size(); x++) {
										Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
										kickers.add(listSolo.get(i));
										kickers.add(listSolo.get(j));
										kickers.add(listSolo.get(x));
										hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
									}
								}
							}
							break;
						case 4:
							for (int i = 0; i < listSolo.size() - 3; i++) {
								for (int j = i + 1; j < listSolo.size() - 2; j++) {
									for (int x = j + 1; x < listSolo.size() - 1; x++) {
										for (int y = x + 1; y < listSolo.size(); y++) {
											Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
											kickers.add(listSolo.get(i));
											kickers.add(listSolo.get(j));
											kickers.add(listSolo.get(x));
											kickers.add(listSolo.get(y));
											hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
										}
									}
								}
							}
							break;
						// 5涓互涓婂彲鑳借鍑犱箮涓� ,,鑰屼笖璧风爜20寮犵墝
						default:
							break;
						}
					}

				}
			}

			// TRIO_CHAIN_PAIR
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() != 0)
				for (Hand hand3 : listTrioChain) {
					int len = hand3.getLen();
					List<PokerType> listPari = new ArrayList<PokerType>();
					int h_key = hand3.getKey().ordinal();
					// 鎵惧嚭鍓╀綑鏈夊瀛�
					for (int i = 0; i < PokerType.BJOKER.ordinal(); i++) {
						if (cards[i] > 1) {
							if (i >= h_key && i < h_key + len)
								continue;
							else {
								listPari.add(PokerType.values()[i]);
							}
						}
					}
					if (listPari.size() >= len) {
						switch (len) {
						case 2:
							for (int i = 0; i < listPari.size() - 1; i++) {
								for (int j = i + 1; j < listPari.size(); j++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listPari.get(i));
									kickers.add(listPari.get(j));
									hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
								}
							}

							break;
						case 3:
							for (int i = 0; i < listPari.size() - 2; i++) {
								for (int j = i + 1; j < listPari.size() - 1; j++) {
									for (int x = j + 1; x < listPari.size(); x++) {
										Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
										kickers.add(listPari.get(i));
										kickers.add(listPari.get(j));
										kickers.add(listPari.get(x));
										hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
									}
								}
							}
							break;
						// 4涓互涓婁笉绠�,
						default:
							break;
						}
					}
					hands.add(hand3);
				}
			for (int i = 0; i < PokerType.BJOKER.ordinal(); i++) {
				// BOMB
				if (cards[i] == 4) {
					listBomb.add(PokerType.values()[i]);
				}
				// TRIO
				if (cards[i] > 2) {
					// listTrio.add(PokerType.values()[i]);
					for (int j = 0; j <= PokerType.RJOKER.ordinal(); j++) {
						if (cards[j] > 0 && j != i) {
							Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
							kickers.add(PokerType.values()[j]);
							Hand hand2 = new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers);
							if (cards[i] == 4) {// 如果三张的牌是炸弹或者带的牌是3张及以上的张数，就是坏的打法
								if (cards[j] > 1) {
									BadHand.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
								}
								BadHand.add(hand2);
							} else {

								if (cards[j] == 1) {// TRIO_SOLO
									hands.add(hand2);
								} else if (cards[j] == 2) {
									// TRIO_SOLO
									hands.add(hand2);
									// TRIO_PAIR
									hands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
								} else {
									BadHand.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
									BadHand.add(new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers));
								}

							}
						}
					}
					// TRIO
					if (cards[i] == 4) {
						BadHand.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					}

				}

			}

			// FOUR_DUAL_SOLO
			for (PokerType pokerType : listBomb) {
				int key = pokerType.ordinal();
				for (int j = 0; j <= PokerType.TWO.ordinal(); j++) {
					if (cards[j] > 0 && j != key) {
						for (int x = j + 1; x <= PokerType.RJOKER.ordinal(); x++)
							if (cards[x] > 0 && x != key) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(PokerType.values()[j]);
								kickers.add(PokerType.values()[x]);
								Hand hand2 = new Hand(HandType.FOUR_DUAL_SOLO, pokerType, 1, kickers);
								if (cards[x] > 1 || cards[j] > 1) {
									BadHand.add(hand2);
								} else {
									hands.add(hand2);
								}

								// System.out.println(hand2.toString());
							}

					}
				}
			}
			// FOUR_DUAL_PAIR
			for (PokerType pokerType : listBomb) {
				int key = pokerType.ordinal();
				for (int j = 0; j <= PokerType.ACE.ordinal(); j++) {
					if (cards[j] > 1 && j != key) {
						for (int x = j + 1; x <= PokerType.TWO.ordinal(); x++)
							if (cards[x] > 1 && x != key) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(PokerType.values()[j]);
								kickers.add(PokerType.values()[x]);
								Hand hand2 = new Hand(HandType.FOUR_DUAL_PAIR, pokerType, 1, kickers);
								if (cards[x] > 2 || cards[j] > 2) {
									BadHand.add(hand2);
								} else {
									hands.add(hand2);
								}
								// System.out.println(hand2.toString());
							}
					}
				}
			}
			for (int i = PokerType.RJOKER.ordinal(); i >= 0; i--) {
				if (cards[i] == 4) {
					BadHand.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					BadHand.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
				} else {
					// PAIR
					if (cards[i] > 1) {
						hands.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					}
					// SOLO
					if (cards[i] > 0) {
						hands.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
					}
				}
			}
			if (BadHand.size() > 0) {
				for (Hand hand2 : BadHand) {
					hands.add(hand2);
				}
			}
			// BOMB
			for (PokerType pokerType : listBomb) {
				hands.add(new Hand(HandType.BOMB, pokerType, 1, null));
			}
			// NUKE
			if (cards[PokerType.BJOKER.ordinal()] > 0 && cards[PokerType.RJOKER.ordinal()] > 0)
				hands.add(new Hand(HandType.NUKE, PokerType.BJOKER, 1, null));
			return hands;
		}

		// 以下是跟出的代码
		HandType handtype = hand.getType();
		PokerType pokerType = hand.getKey();
		int pokerNum = pokerType.ordinal();
		int len = hand.getLen();
		// 是否又双王
		if (cards[PokerType.RJOKER.ordinal()] != 0 && cards[PokerType.BJOKER.ordinal()] != 0)
			hands.add(new Hand(HandType.NUKE, PokerType.BJOKER, 1, null));
		// 鑾峰彇鐐稿脊
		if (hand.getType() != HandType.BOMB && hand.getType() != HandType.NUKE) {
			for (int i = PokerType.TWO.ordinal(); i >= 0; i--) {
				if (cards[i] == 4) {
					hands.add(new Hand(HandType.BOMB, PokerType.values()[i], 1, null));
				}
			}
		}
		switch (handtype.ordinal()) {
		case 0: // SOLO
			for (int i = PokerType.RJOKER.ordinal(); i > pokerNum; i--) {
				if (cards[i] > 0) {
					if (cards[i] > 2) {
						BadHand.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
					}
				}
			}
			break;
		case 1: // PAIR
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] > 1) {
					if (cards[i] == 4) {
						BadHand.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					}

				}

			}
			break;
		case 2: // TRIO
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] > 2) {
					if (cards[i] == 4) {
						BadHand.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					}
				}

			}
			break;
		case 3: // TRIO_SOLO
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] > 2) {
					byte temp = cards[i];
					// cards[i]-=4;
					cards[i] = 0;
					for (int j = 0; j <= PokerType.RJOKER.ordinal(); j++) {
						if (cards[j] > 0) {
							Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
							kickers.add(PokerType.values()[j]);
							if (cards[i] == 4 || cards[j] > 2) {
								BadHand.add(new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers));
							} else {
								hands.add(new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers));
							}

						}
					}
					cards[i] = temp;
				}

			}

			break;
		case 4: // TRIO_PAIR
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] > 2) {
					if (cards[i] == 4 && hands.size() != 0)
						continue;
					else {
						cards[i] -= 3;
						for (int j = 0; j <= PokerType.TWO.ordinal(); j++) {
							if (cards[j] > 1) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(PokerType.values()[j]);
								if (cards[i] == 4 || cards[j] > 2) {
									BadHand.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
								} else {
									hands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
								}

							}
						}
						cards[i] += 3;
					}
				}
			}

			break;
		case 5: // BOMB
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] == 4) {
					hands.add(new Hand(HandType.BOMB, PokerType.values()[i], 1, null));
				}

			}
			break;

		case 6: // NUKE
			break;
		case 7: // FOUR_DUAL_SOLO
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] == 4) {
					cards[i] = 0;
					for (int j = 0; j <= PokerType.RJOKER.ordinal(); j++) {
						if (cards[j] > 0) {
							for (int x = j + 1; x <= PokerType.TWO.ordinal(); x++)
								if (cards[x] > 0 && x != j) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(PokerType.values()[j]);
									kickers.add(PokerType.values()[x]);
									if (cards[j] > 2 || cards[x] > 2) {
										BadHand.add(
												new Hand(HandType.FOUR_DUAL_SOLO, PokerType.values()[i], 1, kickers));
									} else {
										hands.add(new Hand(HandType.FOUR_DUAL_SOLO, PokerType.values()[i], 1, kickers));
									}

								}
						}
					}
					cards[i] = 4;
				}
			}

			break;
		case 8: // FOUR_DUAL_PAIR
			for (int i = PokerType.TWO.ordinal(); i > pokerNum; i--) {
				if (cards[i] == 4) {
					cards[i] = 0;
					for (int j = 0; j <= PokerType.BJOKER.ordinal(); j++) {
						if (cards[j] > 1) {
							for (int x = j + 1; x <= PokerType.TWO.ordinal(); x++)
								if (cards[x] > 1 && x != j) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(PokerType.values()[j]);
									kickers.add(PokerType.values()[x]);
									hands.add(new Hand(HandType.FOUR_DUAL_PAIR, PokerType.values()[i], 1, kickers));
								}
						}
					}
					cards[i] = 0;
				}
			}

			break;
		case 9: // SOLO_CHAIN
			for (int i = pokerNum + 1; i <= PokerType.TEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] != 0 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.SOLO_CHAIN, PokerType.values()[i], len, null);
						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					hands.add(hand2);
					hand2 = null;
				}
			}
			break;
		case 10: // PAIR_CHAIN
			for (int i = pokerNum + 1; i <= PokerType.QUEEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 1 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.PAIR_CHAIN, PokerType.values()[i], len, null);
						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					hands.add(hand2);
					hand2 = null;
				}
			}
			break;
		case 11: // TRIO_CHAIN
			for (int i = pokerNum + 1; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], len, null);
						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					hands.add(hand2);
					hand2 = null;
				}
			}
			break;
		case 12: // TRIO_CHAIN_SOLO
			List<Hand> listTrioChain = new ArrayList<Hand>();
			// 鍏堟壘鍑轰笁椤轰笉甯�
			for (int i = pokerNum + 1; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], len, null);

						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					listTrioChain.add(hand2);
					hand2 = null;
				}
			}
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() == 0)
				break;
			for (Hand hand3 : listTrioChain) {
				List<PokerType> listSolo = new ArrayList<PokerType>();
				int h_key = hand3.getKey().ordinal();
				// 鎵惧嚭鍓╀綑鏈夊崟涓�
				for (int i = 0; i <= PokerType.RJOKER.ordinal(); i++) {
					if (cards[i] > 0) {
						if (i >= h_key && i < h_key + len)
							continue;
						else {
							listSolo.add(PokerType.values()[i]);
						}
					}
				}
				if (listSolo.size() >= len) {
					switch (len) {
					case 2:
						for (int i = 0; i < listSolo.size() - 1; i++) {
							for (int j = i + 1; j < listSolo.size(); j++) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(listSolo.get(i));
								kickers.add(listSolo.get(j));
								hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
							}
						}

						break;
					case 3:
						for (int i = 0; i < listSolo.size() - 2; i++) {
							for (int j = i + 1; j < listSolo.size() - 1; j++) {
								for (int x = j + 1; x < listSolo.size(); x++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listSolo.get(i));
									kickers.add(listSolo.get(j));
									kickers.add(listSolo.get(x));
									hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
								}
							}
						}
						break;
					case 4:
						for (int i = 0; i < listSolo.size() - 3; i++) {
							for (int j = i + 1; j < listSolo.size() - 2; j++) {
								for (int x = j + 1; x < listSolo.size() - 1; x++) {
									for (int y = x + 1; y < listSolo.size(); y++) {
										Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
										kickers.add(listSolo.get(i));
										kickers.add(listSolo.get(j));
										kickers.add(listSolo.get(x));
										kickers.add(listSolo.get(y));
										hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
									}
								}
							}
						}
						break;
					}
				}

			}

			break;
		case 13: // TRIO_CHAIN_PAIR
			listTrioChain = new ArrayList<Hand>();
			// 鍏堟壘鍑轰笁椤轰笉甯�
			for (int i = pokerNum + 1; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], len, null);

						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					listTrioChain.add(hand2);
					hand2 = null;
				}
			}
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() == 0)
				break;
			for (Hand hand3 : listTrioChain) {
				List<PokerType> listPari = new ArrayList<PokerType>();
				int h_key = hand3.getKey().ordinal();
				// 鎵惧嚭鍓╀綑鏈夊崟涓�
				for (int i = 0; i <= PokerType.TWO.ordinal(); i++) {
					if (cards[i] > 1) {
						if (i >= h_key && i < h_key + len)
							continue;
						else {
							listPari.add(PokerType.values()[i]);
						}
					}
				}
				if (listPari.size() >= len) {
					switch (len) {
					case 2:
						for (int i = 0; i < listPari.size() - 1; i++) {
							for (int j = i + 1; j < listPari.size(); j++) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(listPari.get(i));
								kickers.add(listPari.get(j));
								hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
							}
						}

						break;
					case 3:
						for (int i = 0; i < listPari.size() - 2; i++) {
							for (int j = i + 1; j < listPari.size() - 1; j++) {
								for (int x = j + 1; x < listPari.size(); x++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listPari.get(i));
									kickers.add(listPari.get(j));
									kickers.add(listPari.get(x));
									hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
								}
							}
						}
						break;
					}
				}

			}

			break;
		}
		if (BadHand.size() > 0) {
			for (Hand hand2 : BadHand) {
				hands.add(hand2);
			}
		}
		hands.add(null);
		return hands;

	}

	public static List<Hand> GetMovesDown(Hand hand, byte[] cards) {
		List<Hand> hands = new ArrayList<Hand>();
		List<Hand> BadHands = new ArrayList<Hand>();
		if (hand == null) {
			List<PokerType> listBomb = new ArrayList<PokerType>();
			List<Hand> listTrioChain = new ArrayList<Hand>();
			// SOLO_CHAIN
			for (int i = 0; i <= PokerType.TEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] != 0 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num > 4) {
						hand2 = new Hand(HandType.SOLO_CHAIN, PokerType.values()[i], num, null);
						hands.add(hand2);
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {

					hand2 = null;
				}
			}
			// PAIR_CHAIN
			for (int i = 0; i <= PokerType.QUEEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 1 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num > 2) {
						hand2 = new Hand(HandType.PAIR_CHAIN, PokerType.values()[i], num, null);
						hands.add(hand2);
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {

					hand2 = null;
				}
			}

			// TRIO_CHAIN
			for (int i = 0; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num > 1) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], num, null);
						listTrioChain.add(hand2);
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {

					hand2 = null;
				}
			}
			//
			// TRIO_CHAIN_SOLO
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() != 0) {
				for (Hand hand3 : listTrioChain) {
					int len = hand3.getLen();
					List<PokerType> listSolo = new ArrayList<PokerType>();
					int h_key = hand3.getKey().ordinal();
					// 鎵惧嚭鍓╀綑鏈夊崟涓�
					for (int i = 0; i <= PokerType.RJOKER.ordinal(); i++) {
						if (cards[i] > 0) {
							if (i >= h_key && i < h_key + len)
								continue;
							else {
								listSolo.add(PokerType.values()[i]);
							}
						}
					}
					if (listSolo.size() >= len) {
						switch (len) {
						case 2:
							for (int i = 0; i < listSolo.size() - 1; i++) {
								for (int j = i + 1; j < listSolo.size(); j++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listSolo.get(i));
									kickers.add(listSolo.get(j));
									hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));

								}
							}

							break;
						case 3:
							for (int i = 0; i < listSolo.size() - 2; i++) {
								for (int j = i + 1; j < listSolo.size() - 1; j++) {
									for (int x = j + 1; x < listSolo.size(); x++) {
										Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
										kickers.add(listSolo.get(i));
										kickers.add(listSolo.get(j));
										kickers.add(listSolo.get(x));
										hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
									}
								}
							}
							break;
						case 4:
							for (int i = 0; i < listSolo.size() - 3; i++) {
								for (int j = i + 1; j < listSolo.size() - 2; j++) {
									for (int x = j + 1; x < listSolo.size() - 1; x++) {
										for (int y = x + 1; y < listSolo.size(); y++) {
											Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
											kickers.add(listSolo.get(i));
											kickers.add(listSolo.get(j));
											kickers.add(listSolo.get(x));
											kickers.add(listSolo.get(y));
											hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
										}
									}
								}
							}
							break;
						// 5涓互涓婂彲鑳借鍑犱箮涓� ,,鑰屼笖璧风爜20寮犵墝
						default:
							break;
						}
					}

				}
			}

			// TRIO_CHAIN_PAIR
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() != 0)
				for (Hand hand3 : listTrioChain) {
					int len = hand3.getLen();
					List<PokerType> listPari = new ArrayList<PokerType>();
					int h_key = hand3.getKey().ordinal();
					// 鎵惧嚭鍓╀綑鏈夊瀛�
					for (int i = 0; i < PokerType.BJOKER.ordinal(); i++) {
						if (cards[i] > 1) {
							if (i >= h_key && i < h_key + len)
								continue;
							else {
								listPari.add(PokerType.values()[i]);
							}
						}
					}
					if (listPari.size() >= len) {
						switch (len) {
						case 2:
							for (int i = 0; i < listPari.size() - 1; i++) {
								for (int j = i + 1; j < listPari.size(); j++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listPari.get(i));
									kickers.add(listPari.get(j));
									hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
								}
							}

							break;
						case 3:
							for (int i = 0; i < listPari.size() - 2; i++) {
								for (int j = i + 1; j < listPari.size() - 1; j++) {
									for (int x = j + 1; x < listPari.size(); x++) {
										Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
										kickers.add(listPari.get(i));
										kickers.add(listPari.get(j));
										kickers.add(listPari.get(x));
										hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
									}
								}
							}
							break;
						// 4涓互涓婁笉绠�,
						default:
							break;
						}
					}
					hands.add(hand3);
				}
			for (int i = 0; i < PokerType.BJOKER.ordinal(); i++) {
				// BOMB
				if (cards[i] == 4) {
					listBomb.add(PokerType.values()[i]);
					// hands.add(new
					// Hand(HandType.BOMB,PokerType.values()[i],1,null));
				}
				// TRIO
				if (cards[i] > 2) {
					// listTrio.add(PokerType.values()[i]);
					for (int j = 0; j <= PokerType.RJOKER.ordinal(); j++) {
						if (cards[j] > 0 && j != i) {
							Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
							kickers.add(PokerType.values()[j]);

							Hand hand2 = new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers);
							if (cards[i] == 4) {
								if (cards[j] > 1) {
									BadHands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
								}
								BadHands.add(hand2);
							} else {
								if (cards[j] == 1) { // TRIO_SOLO
									hands.add(hand2);
								} else if (cards[j] == 2) {
									// TRIO_SOLO
									hands.add(hand2);
									// TRIO_PAIR
									hands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
								} else {
									BadHands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
									BadHands.add(new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers));
								}

							}

						}
					}
					if (cards[i] == 4) {
						BadHands.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					} else {
						// TRIO
						hands.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					}

				}

			}

			// FOUR_DUAL_SOLO
			for (PokerType pokerType : listBomb) {
				int key = pokerType.ordinal();
				for (int j = 0; j <= PokerType.TWO.ordinal(); j++) {
					if (cards[j] > 0 && j != key) {
						for (int x = j + 1; x <= PokerType.RJOKER.ordinal(); x++)
							if (cards[x] > 0 && x != key) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(PokerType.values()[j]);
								kickers.add(PokerType.values()[x]);
								Hand hand2 = new Hand(HandType.FOUR_DUAL_SOLO, pokerType, 1, kickers);
								if (cards[x] > 2 || cards[j] > 2) {
									BadHands.add(hand2);
								} else {
									hands.add(hand2);
								}
								// System.out.println(hand2.toString());
							}

					}
				}
			}
			// FOUR_DUAL_PAIR
			for (PokerType pokerType : listBomb) {
				int key = pokerType.ordinal();
				for (int j = 0; j <= PokerType.ACE.ordinal(); j++) {
					if (cards[j] > 1 && j != key) {
						for (int x = j + 1; x <= PokerType.TWO.ordinal(); x++)
							if (cards[x] > 1 && x != key) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(PokerType.values()[j]);
								kickers.add(PokerType.values()[x]);
								Hand hand2 = new Hand(HandType.FOUR_DUAL_PAIR, pokerType, 1, kickers);
								if (cards[x] > 2 || cards[j] > 2) {
									BadHands.add(hand2);
								} else {
									hands.add(hand2);
								}
								// System.out.println(hand2.toString());
							}

					}
				}
			}

			// 浠庡皬鍒板ぇ鎼滅储
			for (int i = 0; i <= PokerType.RJOKER.ordinal(); i++) {
				if (cards[i] == 4) {
					BadHands.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					BadHands.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
				} else {
					// PAIR
					if (cards[i] > 1) {
						hands.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					}
					// SOLO
					if (cards[i] > 0) {
						hands.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
					}
				}

			}

			if (BadHands.size() > 0) {
				for (Hand hand2 : BadHands) {
					hands.add(hand2);
				}
			}
			// BOMB
			for (PokerType pokerType : listBomb) {
				hands.add(new Hand(HandType.BOMB, pokerType, 1, null));
			}
			// NUKE
			if (cards[PokerType.BJOKER.ordinal()] > 0 && cards[PokerType.RJOKER.ordinal()] > 0)
				hands.add(new Hand(HandType.NUKE, PokerType.BJOKER, 1, null));
			return hands;
		}

		HandType handtype = hand.getType();
		PokerType pokerType = hand.getKey();
		int pokerNum = pokerType.ordinal();
		int len = hand.getLen();
		if (cards[PokerType.RJOKER.ordinal()] != 0 && cards[PokerType.BJOKER.ordinal()] != 0)
			hands.add(new Hand(HandType.NUKE, PokerType.BJOKER, 1, null));
		// 鑾峰彇鐐稿脊
		if (hand.getType() != HandType.BOMB && hand.getType() != HandType.NUKE) {
			for (int i = 0; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] == 4) {
					hands.add(new Hand(HandType.BOMB, PokerType.values()[i], 1, null));
				}
			}

		}
		switch (handtype.ordinal()) {
		case 0: // SOLO
			for (int i = pokerNum + 1; i <= PokerType.RJOKER.ordinal(); i++) {
				if (cards[i] > 0) {
					if (cards[i] > 2) {
						BadHands.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
					}

				}
			}
			break;
		case 1: // PAIR
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] > 1) {
					if (cards[i] == 4) {
						BadHands.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
					}

				}

			}
			break;
		case 2: // TRIO
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] > 2) {
					if (cards[i] == 4) {
						BadHands.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					} else {
						hands.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
					}

				}

			}
			break;
		case 3: // TRIO_SOLO
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] > 2) {
					byte temp = cards[i];
					// cards[i]-=4;
					cards[i] = 0;
					for (int j = 0; j <= PokerType.RJOKER.ordinal(); j++) {
						if (cards[j] > 0) {
							Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
							kickers.add(PokerType.values()[j]);
							if (cards[i] == 4 || cards[j] > 2) {
								BadHands.add(new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers));
							} else {
								hands.add(new Hand(HandType.TRIO_SOLO, PokerType.values()[i], 1, kickers));
							}

						}
					}
					cards[i] = temp;

				}

			}

			break;
		case 4: // TRIO_PAIR
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] > 2) {
					cards[i] -= 3;
					for (int j = 0; j <= PokerType.TWO.ordinal(); j++) {
						if (cards[j] > 1) {
							Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
							kickers.add(PokerType.values()[j]);
							if (cards[i] == 4 || cards[j] > 2) {
								BadHands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
							} else {
								hands.add(new Hand(HandType.TRIO_PAIR, PokerType.values()[i], 1, kickers));
							}

						}
					}
					cards[i] += 3;

				}
			}

			break;
		case 5: // BOMB
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] == 4) {
					hands.add(new Hand(HandType.BOMB, PokerType.values()[i], 1, null));
				}

			}
			break;

		case 6: // NUKE
			break;
		case 7: // FOUR_DUAL_SOLO
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] == 4) {
					cards[i] = 0;
					for (int j = 0; j <= PokerType.RJOKER.ordinal(); j++) {
						if (cards[j] > 0) {
							for (int x = j + 1; x <= PokerType.TWO.ordinal(); x++)
								if (cards[x] > 0 && x != j) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(PokerType.values()[j]);
									kickers.add(PokerType.values()[x]);
									if (cards[j] > 2 || cards[x] > 2) {
										BadHands.add(
												new Hand(HandType.FOUR_DUAL_PAIR, PokerType.values()[i], 1, kickers));
									} else {
										hands.add(new Hand(HandType.FOUR_DUAL_PAIR, PokerType.values()[i], 1, kickers));
									}

								}
						}
					}
					cards[i] = 4;
				}
			}

			break;
		case 8: // FOUR_DUAL_PAIR
			for (int i = pokerNum + 1; i <= PokerType.TWO.ordinal(); i++) {
				if (cards[i] == 4) {
					cards[i] = 0;
					for (int j = 0; j <= PokerType.BJOKER.ordinal(); j++) {
						if (cards[j] > 1) {
							for (int x = j + 1; x <= PokerType.TWO.ordinal(); x++)
								if (cards[x] > 1 && x != j) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(PokerType.values()[j]);
									kickers.add(PokerType.values()[x]);
									hands.add(new Hand(HandType.FOUR_DUAL_PAIR, PokerType.values()[i], 1, kickers));
								}
						}
					}
					cards[i] = 4;
				}
			}

			break;
		case 9: // SOLO_CHAIN
			for (int i = pokerNum + 1; i <= PokerType.TEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] != 0 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.SOLO_CHAIN, PokerType.values()[i], len, null);
						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					hands.add(hand2);
					hand2 = null;
				}
			}
			break;
		case 10: // PAIR_CHAIN
			for (int i = pokerNum + 1; i <= PokerType.QUEEN.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 1 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.PAIR_CHAIN, PokerType.values()[i], len, null);
						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					hands.add(hand2);
					hand2 = null;
				}
			}
			break;
		case 11: // TRIO_CHAIN
			for (int i = pokerNum + 1; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], len, null);
						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					hands.add(hand2);
					hand2 = null;
				}
			}
			break;
		case 12: // TRIO_CHAIN_SOLO
			List<Hand> listTrioChain = new ArrayList<Hand>();
			// 鍏堟壘鍑轰笁椤轰笉甯�
			for (int i = pokerNum + 1; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], len, null);

						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					listTrioChain.add(hand2);
					hand2 = null;
				}
			}
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() == 0)
				break;
			for (Hand hand3 : listTrioChain) {
				List<PokerType> listSolo = new ArrayList<PokerType>();
				int h_key = hand3.getKey().ordinal();
				// 鎵惧嚭鍓╀綑鏈夊崟涓�
				for (int i = 0; i <= PokerType.RJOKER.ordinal(); i++) {
					if (cards[i] > 0) {
						if (i >= h_key && i < h_key + len)
							continue;
						else {
							listSolo.add(PokerType.values()[i]);
						}
					}
				}
				if (listSolo.size() >= len) {
					switch (len) {
					case 2:
						for (int i = 0; i < listSolo.size() - 1; i++) {
							for (int j = i + 1; j < listSolo.size(); j++) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(listSolo.get(i));
								kickers.add(listSolo.get(j));
								hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
							}
						}

						break;
					case 3:
						for (int i = 0; i < listSolo.size() - 2; i++) {
							for (int j = i + 1; j < listSolo.size() - 1; j++) {
								for (int x = j + 1; x < listSolo.size(); x++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listSolo.get(i));
									kickers.add(listSolo.get(j));
									kickers.add(listSolo.get(x));
									hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
								}
							}
						}
						break;
					case 4:
						for (int i = 0; i < listSolo.size() - 3; i++) {
							for (int j = i + 1; j < listSolo.size() - 2; j++) {
								for (int x = j + 1; x < listSolo.size() - 1; x++) {
									for (int y = x + 1; y < listSolo.size(); y++) {
										Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
										kickers.add(listSolo.get(i));
										kickers.add(listSolo.get(j));
										kickers.add(listSolo.get(x));
										kickers.add(listSolo.get(y));
										hands.add(new Hand(HandType.TRIO_CHAIN_SOLO, hand3.getKey(), len, kickers));
									}
								}
							}
						}
						break;
					}
				}

			}

			break;
		case 13: // TRIO_CHAIN_PAIR
			listTrioChain = new ArrayList<Hand>();
			// 鍏堟壘鍑轰笁椤轰笉甯�
			for (int i = pokerNum + 1; i <= PokerType.KING.ordinal(); i++) {
				int j = i;
				int num = 0;
				Hand hand2 = null;
				while (cards[j] > 2 && j < PokerType.TWO.ordinal()) {
					num++;
					if (num == len) {
						hand2 = new Hand(HandType.TRIO_CHAIN, PokerType.values()[i], len, null);

						break;
					}
					j++;
				}
				if (hand2 == null) {
					i = j;
				} else {
					listTrioChain.add(hand2);
					hand2 = null;
				}
			}
			// 濡傛灉娌℃湁璇ョ被鍨嬬殑灏眀reak
			if (listTrioChain.size() == 0)
				break;
			for (Hand hand3 : listTrioChain) {
				List<PokerType> listPari = new ArrayList<PokerType>();
				int h_key = hand3.getKey().ordinal();
				// 鎵惧嚭鍓╀綑鏈夊崟涓�
				for (int i = 0; i <= PokerType.TWO.ordinal(); i++) {
					if (cards[i] > 1) {
						if (i >= h_key && i < h_key + len)
							continue;
						else {
							listPari.add(PokerType.values()[i]);
						}
					}
				}
				if (listPari.size() >= len) {
					switch (len) {
					case 2:
						for (int i = 0; i < listPari.size() - 1; i++) {
							for (int j = i + 1; j < listPari.size(); j++) {
								Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
								kickers.add(listPari.get(i));
								kickers.add(listPari.get(j));
								hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
							}
						}

						break;
					case 3:
						for (int i = 0; i < listPari.size() - 2; i++) {
							for (int j = i + 1; j < listPari.size() - 1; j++) {
								for (int x = j + 1; x < listPari.size(); x++) {
									Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
									kickers.add(listPari.get(i));
									kickers.add(listPari.get(j));
									kickers.add(listPari.get(x));
									hands.add(new Hand(HandType.TRIO_CHAIN_PAIR, hand3.getKey(), len, kickers));
								}
							}
						}
						break;
					}
				}

			}

			break;
		}
		if (BadHands.size() > 0) {
			for (Hand hand2 : BadHands) {
				hands.add(hand2);
			}
		}
		hands.add(null);
		return hands;

	}

	/**
	 * 涓嬪璧版硶鐢熸垚 鏈嚱鏁版槸涓哄崟涓垨鑰呭瀛愭儏鍐佃皟搴�
	 */
	private static List<Hand> DownPlayerMovesSort(Hand hand, byte[] DownCards, byte[] UpCards) {
		int typeNum = 0;
		List<Hand> hands = new ArrayList<Hand>();
		for (int i = 0; i < UpCards.length; i++) {
			if (UpCards[i] > 0)
				typeNum++;
		}
		if (typeNum == 1) {
			// 鍙兘鏄�鍗曚釜,瀵瑰瓙,涓変笉甯�鐐稿脊,鐐稿脊涓嶇
			for (int i = 0; i < UpCards.length; i++) {
				if (UpCards[i] > 0) {
					int num = UpCards[i];
					// 涓婂鍙墿涓嬩竴寮�
					if (num == 1) {
						// 涓嬪鍑虹墝.鎵惧嚭灏忎簬涓婂鐨勭墝
						if (hand == null) {
							for (int x = 0; x < i; x++) {
								if (DownCards[x] > 0) {
									hands.add(new Hand(HandType.SOLO, PokerType.values()[x], 1, null));
									return hands;
								}
							}
						}
						// 璺熷嚭
						else {
							// 濡傛灉涓婂鍑虹墝涓哄崟锛屼笖灏忎簬涓婂鐨勭墝
							if (hand.getType() == HandType.SOLO && hand.getKey().ordinal() < i) {
								hands.add(null);
								return hands;
							}

						}
					} else if (num == 2) {
						// 涓嬪鍑虹墝.鎵惧嚭灏忎簬涓婂鐨勭墝
						if (hand == null) {
							for (int x = 0; x < i; x++) {
								if (DownCards[x] > 1) {
									hands.add(new Hand(HandType.PAIR, PokerType.values()[x], 1, null));
									return hands;
								}
							}

						}
						// 璺熷嚭
						else {
							// 濡傛灉涓婂鍑虹墝涓哄崟锛屼笖灏忎簬涓婂鐨勭墝
							if (hand.getType() == HandType.PAIR && hand.getKey().ordinal() < i) {
								hands.add(null);
								return hands;
							}

						}
					} else if (num == 3) {
						// 涓嬪鍑虹墝.鎵惧嚭灏忎簬涓婂鐨勭墝
						if (hand == null) {
							for (int x = 0; x < i; x++) {
								if (DownCards[x] > 2) {
									hands.add(new Hand(HandType.TRIO, PokerType.values()[x], 1, null));
									return hands;
								}
							}
						}
						// 璺熷嚭
						else {
							// 濡傛灉涓婂鍑虹墝涓哄崟锛屼笖灏忎簬涓婂鐨勭墝
							if (hand.getType() == HandType.TRIO && hand.getKey().ordinal() < i) {
								hands.add(null);
								return hands;
							}

						}
					}
					break;
				}
			}
		}
		// 姝ｅ父鎼滅储
		hands = GetMovesDown(hand, DownCards);
		return hands;
	}

	private static Random rand;
	static {
		long seed = System.nanoTime();
		rand = new Random(seed);
	}

	public static void cardsToString(byte[][] cards, String[] cardsStr1) {
		int i = 0;
		for (byte bs[] : cards) {
			for (int j = 0; j < 15; j++) {
				while (bs[j]-- > 0) {
					if (j > 6) {
						switch (j) {
						case 7:
							cardsStr1[i] += "T";
							break;
						case 8:
							cardsStr1[i] += "J";
							break;
						case 9:
							cardsStr1[i] += "Q";
							break;
						case 10:
							cardsStr1[i] += "K";
							break;
						case 11:
							cardsStr1[i] += "A";
							break;
						case 12:
							cardsStr1[i] += "2";
							break;
						case 13:
							cardsStr1[i] += "X";
							break;
						case 14:
							cardsStr1[i] += "D";
							break;
						}
					} else
						cardsStr1[i] += j + 3;
				}
			}
			i++;
		}
	}

	public static int randomCards(byte[][] cards, int pokerTypeNum, int cardNum) {
		Map<Integer, Integer> allCards = new HashMap<Integer, Integer>();
		for (int i = 0; i < 13; i++) {
			allCards.put(i, 4);
		}
		allCards.put(13, 1);
		allCards.put(14, 1);
		Set<Integer> p1 = new HashSet<Integer>();
		Set<Integer> p2 = new HashSet<Integer>();
		Set<Integer> p3 = new HashSet<Integer>();

		int[] playerType = new int[3];
		int rd1 = rand.nextInt(pokerTypeNum - 5) + 2;
		int rd2 = rand.nextInt(pokerTypeNum - 5) + 2;
		int rd3 = rand.nextInt(pokerTypeNum - 5) + 2;
		while ((rd1 + rd2 + rd3) != pokerTypeNum) {
			rd1 = rand.nextInt(pokerTypeNum - 5) + 2;
			rd2 = rand.nextInt(pokerTypeNum - 5) + 2;
			rd3 = rand.nextInt(pokerTypeNum - 5) + 2;
		}
		playerType[0] = rd1;
		playerType[1] = rd2;
		playerType[2] = rd3;
		int temp;
		for (int i = 0; i < 3; i++) {
			while (playerType[i] > 0) {
				temp = rand.nextInt(15);
				int count = allCards.get(temp);
				if (count > 0) {
					if (i == 0) {
						if (p1.contains(temp))
							continue;
						else {
							p1.add(temp);
						}
					} else if (i == 1) {
						if (p2.contains(temp))
							continue;
						else {
							p2.add(temp);
						}
					} else if (i == 2) {
						if (p3.contains(temp))
							continue;
						else {
							p3.add(temp);
						}
					}
					playerType[i]--;
					int s = rand.nextInt(100);
					// 寮犳暟涓嶅悓锛岀敓鎴愮殑寮犳暟涔熶笉鍚�
					switch (count) {
					case 4:
						if (s >= 96)
							cards[i][temp] = 4;
						else if (s < 95 && s >= 81)
							cards[i][temp] = 3;
						else if (s < 80 && s >= 45)
							cards[i][temp] = 2;
						else {
							cards[i][temp] = 1;
						}
						break;
					case 3:
						if (s >= 91)
							cards[i][temp] = 3;
						else if (s < 90 && s >= 50)
							cards[i][temp] = 2;
						else {
							cards[i][temp] = 1;
						}
						break;
					case 2:
						if (s >= 60)
							cards[i][temp] = 2;
						else {
							cards[i][temp] = 1;
						}
						break;
					case 1:
						cards[i][temp] = 1;
						break;
					}
					allCards.put(temp, count - cards[i][temp]);

				}
			}
		}
		return rd1 + rd2 + rd3;
	}

	public String SearchGoodMove(byte[][] playerCards) {
		nodeNumber = 0;
		int outPlayer = statusAdv.getOutHandPlayer();
		Hand outHand = statusAdv.getOutHand();
		int nowPlayer = curPlayer;
		List<Hand> MoveList;
		if (outPlayer == nowPlayer) {
			if (nowPlayer == 1)
				MoveList = DownPlayerMovesSort(null, playerCards[1], playerCards[2]);
			else
				MoveList = GetMoves(null, playerCards[nowPlayer]);
		} else {
			if (nowPlayer == 1)
				MoveList = DownPlayerMovesSort(outHand, playerCards[1], playerCards[2]);
			else
				MoveList = GetMoves(outHand, playerCards[nowPlayer]);
		}
		node = new TreeNode(nowPlayer, outPlayer, outHand, playerCards, 0);
		for (Hand hand : MoveList) {
			MakeMove(hand, node);
			if (IsMustWin(0)) {
				if (nodeNumber > maxNodeNumber) {
					// System.out.println("瓒呰繃瑙勫畾缁撶偣鏁伴噺");
					return "overtime";
				}
				if (node.getCurPlayer() != 0) {
					return hand == null ? "pass" : hand.toString();
				}

			}

			else {
				if (nodeNumber > maxNodeNumber) {
					// System.out.println("瓒呰繃瑙勫畾缁撶偣鏁伴噺");
					return "overtime";
				}
				if (node.getCurPlayer() == 0)
					return hand == null ? "pass" : hand.toString();
			}
			node = node.LastTreeNode;

		}

		return "null";
	}

	// 绛涢�(閫氳繃璁＄畻澶嶆潅搴�
	public static boolean filtrateCards(byte[][] cards) {
		int loadNum = 0;
		int upNum = 0;
		int downNum = 0;
		for (int i = 0; i < 13; i++) {
			loadNum += cards[0][i];
			downNum += cards[1][i];
			upNum += cards[2][i];
		}
		// 鏌愪竴瀹剁墝寮犲ぇ浜�1寮�鎴栬�鏌愪袱瀹跺ぇ浜�4寮�鎴栬�3瀹剁墝澶т簬20 寮犵殑
		if (loadNum > 11 || upNum > 11 || downNum > 11)
			return false;
		if ((loadNum + upNum) > 14 || (loadNum + downNum) > 14 || (upNum + downNum) > 14)
			return false;
		if ((loadNum + upNum + downNum) > 20)
			return false;
		return true;
	}

//	public static void main(String[] args) throws IOException {
//		String[] cards = { "55668777TD", "J", "3456TQQ" };
//		String outCards = "D";
//		int outHandPlayer = 0;
//		int curPlayer = 0;
//		GameStatus status = GameUtils.genStatus(cards, outCards, outHandPlayer, curPlayer);
//		GameStatusAdv statusAdv = new GameStatusAdv(status);
//		// byte[] bcards = statusAdv.getPlayerCards()[0].getCards();
//		//
//		// // Hand hand=statusAdv.getOutHand();
//		// /*
//		// * List<Hand> listHands=GetMoves(hand, bcards); //List<Hand>
//		// listHands=
//		// * DownPlayerMovesSort(hand,bcards,statusAdv.getPlayerCards()[2].
//		// * getCards()); if(listHands==null) System.out.println("null"); else {
//		// * System.out.println("begin"+listHands.size()); for (Hand hand2 :
//		// * listHands) { System.out.println(hand2.toString()); } }
//		// */
//		// // [start]
//		// NewHand nHand = new AISearch(statusAdv).SearchGoodMove();
//		// System.out.println(nHand.hand.toString());
//		long maxTime = 0;
//		long time = 0;
//		int i = 500000;
//		int j = i;
//
//		byte[][] b_cards;
//		System.out.println("begin Search.......");
//		byte[][] copy = new byte[3][15];
//		int over1SecendTimes = 0;
//		int over1_5SecendTimes = 0;
//		int over2SecendTimes = 0;
//		int over5SecendTimes = 0;
//		int over10SecendTimes = 0;
//		int over20SecendTimes = 0;
//		int overtime = 0;
//		int more3count = 0;
//		// GameWriter gameWriter = new GameWriter(".\\GameResult\\", true);
//		while (i-- > 0) {
//			int more3 = 0;
//			b_cards = new byte[3][15];
//			randomCards(b_cards, 13, 0);
//			for (byte[] bs : b_cards) {
//				for (byte b : bs) {
//					if (b > 2) {
//						more3++;
//					}
//				}
//			}
//			if (more3 > 4) {
//				more3count++;
//				continue;
//			}
//
//			for (int a = 0; a < 3; a++)
//				copy[a] = b_cards[a].clone();
//			long t1 = System.currentTimeMillis();
//			String handStr = new AISearch(statusAdv).SearchGoodMove(b_cards);
//
//			long t2 = System.currentTimeMillis() - t1;
//			time += t2;
//			/*
//			 * if(!filtrateCards(copy)) { System.out.println(""+t2+"ms"); String
//			 * []cardsStr1={"","",""}; AISearch.cardsToString(copy, cardsStr1);
//			 * System.out.println(cardsStr1[0]+","+cardsStr1[1]+","+cardsStr1[2]
//			 * +" "+handStr+","+t2+"ms"+",i="+i); }
//			 */
//
//			if (handStr.equals("overtime"))
//				overtime++;
//			if (t2 > 1000) {
//
//				String[] cardsStr1 = { "", "", "" };
//				AISearch.cardsToString(copy, cardsStr1);
//				System.out.println(cardsStr1[0] + "," + cardsStr1[1] + "," + cardsStr1[2] + "	 " + handStr + "," + t2
//						+ "ms" + ",i=" + i);
//				over1SecendTimes++;
//				if (t2 > 1500) {
//					over1_5SecendTimes++;
//					if (t2 > 2000) {
//						over2SecendTimes++;
//						if (t2 > 5000) {
//							over5SecendTimes++;
//							if (t2 > 10000) {
//								over10SecendTimes++;
//								if (t2 > 20000)
//									over20SecendTimes++;
//							}
//						}
//					}
//				}
//			}
//			if (t2 > maxTime) {
//				maxTime = t2;
//			}
//
//		}
//		System.out.println(j + "count");
//		System.out.println("overtime:" + overtime);
//		System.out.println("The average time" + time / j + "ms");
//		System.out.println("More than a second" + over1SecendTimes + "More than1.5 seconds" + over1_5SecendTimes
//				+ "More than 2 seconds" + over2SecendTimes + ",More than 5 seconds" + over5SecendTimes
//				+ ",More than 10 seconds" + over10SecendTimes + ",More than 20 seconds" + over20SecendTimes);
//		System.out.println(more3count);
//	}

}
