package gc2.ds.module;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;
import tw.edu.ncu.csie.lab.core.ai.cells.util.BDICommon;

public class SimpleGUI implements IListenAndSay{
	
	// Frame
	private JFrame jframe;
	private String frontType = "新細明體";
//	private int frontSize = 18;
//	private int frameWidth = 400;//400
//	private int frameHeigh = 650;//650
	private int frontSize = 27;
	private int frameWidth = 600;
	private int frameHeigh = 850;
	// Agent Face
	private ImageIcon imageIcon = new ImageIcon("face1.gif");
	private JLabel face;
	// Agent Talk
	private JTextArea agentTalk;

	private JScrollPane userScroll;
	// User Talk
	private JScrollPane agentScroll;
	private JTextArea userTalk;
	// History
	private JScrollPane historyScroll;
	private JTextArea historyTextArea;
	// Control Button
	private JButton listenBtn;
	private JButton historyBtn;
	private JButton enhanceBtn;
	private JButton backBtn;

	// Data
	private List<String> history = new ArrayList<String>();
	private boolean isAskUserQuestion = false;
	private Queue<String> sentencePool = new LinkedBlockingQueue<String>();
	private Queue<String> userAnswerPool = new LinkedBlockingQueue<String>();
	// Synchronization
	private ReentrantLock lock = new ReentrantLock();
	
	public SimpleGUI(String welcome){
		// Initial
//		String welcome = "你好，我是賽爾斯，很高興認識你。";
        this.history.add("[Agent] "+welcome);
		// Agent Talk
        this.agentTalk = new JTextArea(2,28);
        this.agentTalk.setText(welcome);
        this.agentTalk.setSize(this.frameWidth-10, (this.frameHeigh/12));
        this.agentTalk.setFont(new Font(frontType, Font.PLAIN, frontSize));
        this.agentTalk.setEditable(false);
        this.agentTalk.setLineWrap(true);
        this.agentTalk.setWrapStyleWord(true);
        this.agentScroll = new JScrollPane (this.agentTalk, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // Agent Face
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(this.frameWidth, ((this.frameHeigh*8)/12), Image.SCALE_DEFAULT));
		this.face = new JLabel(imageIcon);
        // User Talk
        this.userTalk = new JTextArea(2,28);
        this.userTalk.setSize(this.frameWidth-10, (this.frameHeigh/12));
        this.userTalk.setFont(new Font(frontType, Font.PLAIN, frontSize));
        this.userTalk.setLineWrap(true);
        this.userTalk.setEditable(false);
        this.userTalk.setWrapStyleWord(true);
        this.userTalk.setBackground(Color.lightGray);
        this.userScroll = new JScrollPane (this.userTalk, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.userTalk.addKeyListener(new MonitorKeyboard());
        // Listen Button
        this.listenBtn = new JButton("Listen");
        this.listenBtn.setActionCommand("listen");
        this.listenBtn.setSize((this.frameWidth/3), (this.frameHeigh/12));
        this.listenBtn.addActionListener(new MonitorButton());
        // History Button
        this.historyBtn = new JButton("History");
        this.historyBtn.setActionCommand("history");
        this.historyBtn.setSize((this.frameWidth/3), (this.frameHeigh/12));
        this.historyBtn.addActionListener(new MonitorButton());
        // Enhance Button
        this.enhanceBtn = new JButton("Enhance");
        this.enhanceBtn.setActionCommand("enhance");
        this.enhanceBtn.setSize((this.frameWidth/3), (this.frameHeigh/12));
       
		// Back Button
        this.backBtn = new JButton("Back");
        this.backBtn.setActionCommand("back");
        this.backBtn.setSize((this.frameWidth/3), (this.frameHeigh/12));
        this.backBtn.addActionListener(new MonitorButton());
        // History
        this.historyTextArea = new JTextArea(22,28);
        this.historyTextArea.setText(this.list2Text(this.history));
        this.historyTextArea.setSize(this.frameWidth-10, this.frameHeigh);
        this.historyTextArea.setFont(new Font(frontType, Font.PLAIN, frontSize));
        this.historyTextArea.setEditable(false);
        this.historyTextArea.setLineWrap(true);
        this.historyTextArea.setWrapStyleWord(true);
        this.historyScroll = new JScrollPane (this.historyTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
        // JFrame
		this.jframe = new JFrame();
		this.jframe.setTitle("Talk");
		this.jframe.setSize(this.frameWidth, this.frameHeigh);
		this.jframe.setLayout(new FlowLayout());
		
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setResizable(false); 
        this.jframe.add(this.agentScroll);
        this.jframe.add(this.face);
        this.jframe.add(this.userScroll);
        this.jframe.add(this.listenBtn);
        this.jframe.add(this.historyBtn);
        this.jframe.add(this.enhanceBtn);
        this.jframe.setVisible(true);
	}
	
	public void setFace(String gif) {
		ImageIcon imageIcon = new ImageIcon(gif);
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(this.frameWidth, ((this.frameHeigh*8)/12), Image.SCALE_DEFAULT));
		this.face.setIcon(imageIcon);
		this.jframe.repaint();
	}
	
	private void showTalk(){
		// Clean
		this.jframe.getContentPane().removeAll();
		// JFrame
		this.jframe.setTitle("Talk");
		this.jframe.setSize(this.frameWidth, this.frameHeigh);
		this.jframe.setLayout(new FlowLayout());
		
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setResizable(false); 
        this.jframe.add(this.agentScroll);
        this.jframe.add(this.face);
        this.jframe.add(this.userScroll);
        this.jframe.add(this.listenBtn);
        this.jframe.add(this.historyBtn);
        this.jframe.add(this.enhanceBtn);
        this.jframe.setVisible(true);
	}
	
	private void showHistory(){
		// Clean
		this.jframe.getContentPane().removeAll();
		this.historyTextArea.setText(this.list2Text(this.history));
		// JFrame
		this.jframe.setTitle("History");
		this.jframe.setSize(this.frameWidth, this.frameHeigh);
		this.jframe.setLayout(new FlowLayout());
        this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jframe.setResizable(false); 
        this.jframe.add(this.historyScroll);
        this.jframe.add(this.backBtn);
        this.jframe.setVisible(true);
	}
	
	private String list2Text(List<String> list){
		StringBuffer text = new StringBuffer();
		for(int i=(list.size()-1); i>-1; i--){
			String line = list.get(i);
			text.append(line+"\n");
		}
		return text.toString().trim();
	}
	
	private class MonitorKeyboard extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String text = userTalk.getText().trim();
				userTalk.setText("");
				if(!text.equals("")){
					if(isAskUserQuestion){
						userAnswerPool.add(text.replace("兩", "二"));
					}else{
						sentencePool.add(text.replace("兩", "二"));
					}
					history.add("[User] "+text);
				}
			}
		}
	}
	
	private class MonitorButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals("history")){
				showHistory();
			}else if(cmd.equals("back")){
				showTalk();
			}else if(cmd.equals("listen")){
				listenBtn.setText("Stop Listen");
				listenBtn.setActionCommand("stop listen");
			}else if(cmd.equals("stop listen")){
				listenBtn.setText("Listen");
				listenBtn.setActionCommand("listen");
			}
		}
	}

	@Override
	public void updateAgentMessage(String agentMessage) {
		lock.lock();
		this.history.add("[Agent] "+agentMessage);
		this.agentTalk.setText(agentMessage);
		lock.unlock();
	}

	@Override
	public Queue<String> getSentencePool() {
		lock.lock();
		Queue<String> sentencePool = this.sentencePool;
		lock.unlock();
		return sentencePool;
	}

	@Override
	public Queue<String> getUserAnswerPool() {
		lock.lock();
		Queue<String> userAnswerPool = this.userAnswerPool;
		lock.unlock();
		return userAnswerPool;
	}
	
	@Override
	public void setUserTalk(Boolean isOpen){
		this.userTalk.setEditable(true);
		this.userTalk.setBackground(Color.WHITE);
	}
	
	@Override
	public List<String> getHistory() {
		return history;
	}

	@Override
	public String getLastAgentSentence() {
		int size = this.history.size();
		for(int i=(size-1); i>0; i--){
			String sentence = this.history.get(i);
			if(sentence.contains("[Agent] ")){
				return sentence.replace("[Agent] ", "");
			}
		}
		return "";
	}

	@Override
	public String getLastUserSentence() {
		int size = this.history.size();
		for(int i=(size-1); i>0; i--){
			String sentence = this.history.get(i);
			if(sentence.contains("[User] ")){
				return sentence.replace("[User] ", "");
			}
		}
		return "";
	}
	
	/**
	 * Get answer of question 
	 * @param question
	 * @return
	 */
	@Override
	public String getAnswer(String question){
		// Ask Question
		this.updateAgentMessage(question);
		// Wait for User Response
		String response = this.waitForUserResponse();
		// Generate Answer
		return response;
	}

	@Override
	public boolean getAnswerTrueFalse(String question) {
		// Ask Question
		this.updateAgentMessage(question);
		// Wait for User Response
		String response = this.waitForUserResponse();
		// Generate Answer
		boolean isAgree = DEJudgement.isAgree(response);
		return isAgree;
	}

	@Override
	public int getAnswerNumber(String question, List<String> options) {
		// Ask Question
		this.updateAgentMessage(question);
		// Say Options
		for(int i=0; i<options.size(); i++){
			switch(i){
				case 0: this.updateAgentMessage("一 "+options.get(i)); break;
				case 1: this.updateAgentMessage("二 "+options.get(i)); break;
				case 2: this.updateAgentMessage("三 "+options.get(i)); break;
				case 3: this.updateAgentMessage("四 "+options.get(i)); break;
				case 4: this.updateAgentMessage("五 "+options.get(i)); break;
				case 5: this.updateAgentMessage("六 "+options.get(i)); break;
				case 6: this.updateAgentMessage("七 "+options.get(i)); break;
				case 7: this.updateAgentMessage("八 "+options.get(i)); break;
				case 8: this.updateAgentMessage("九 "+options.get(i)); break;
				case 9: this.updateAgentMessage("十 "+options.get(i)); break;
				default: break;
			}
		}
		// Wait for User Response
		String response = this.waitForUserResponse();
		// Generate Answer
		int option = DEJudgement.selectNumber(response, options);
		return option;
	}
	
	private String waitForUserResponse(){
		this.isAskUserQuestion = true;
		while(this.userAnswerPool.size()==0){
			BDICommon.sleep(10);
		}
		this.isAskUserQuestion = false;
//		String response = this.userAnswerPool.peek();
//		this.getUserAnswerPool().clear();
		String response = this.userAnswerPool.poll();
		return response;
	}
}

